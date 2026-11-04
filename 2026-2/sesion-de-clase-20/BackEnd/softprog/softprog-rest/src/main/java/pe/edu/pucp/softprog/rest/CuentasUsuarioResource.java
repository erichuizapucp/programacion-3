package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.rest.dto.CuentaUsuarioDTO;
import pe.edu.pucp.softprog.rest.mapper.CuentaUsuarioMapper;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/cuentas-usuario")
@Produces("application/json")
@Consumes("application/json")
public class CuentasUsuarioResource {
    private final CuentaUsuarioBL cuentaUsuarioBL;

    @Context
    private UriInfo uriInfo;

    public CuentasUsuarioResource() {
        cuentaUsuarioBL = new CuentaUsuarioBLImpl();
    }

    @GET
    public Response findAll(@QueryParam("user-name") String userName) {
        try {
            if (userName != null && !userName.isEmpty()) {
                CuentaUsuario cuentaUsuario = cuentaUsuarioBL.findByUserName(userName);
                if (cuentaUsuario == null) {
                    return Response.status(Response.Status.NOT_FOUND)
                            .entity(Map.of("error", "No se encontró la cuenta de usuario: " + userName))
                            .build();
                }
                return Response.ok(List.of(CuentaUsuarioMapper.toDTO(cuentaUsuario)))
                        .build();
            }
            return Response.ok(CuentaUsuarioMapper.toDTOs(cuentaUsuarioBL.findAll()))
                    .build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") int id) {
        try {
            return Response.ok(CuentaUsuarioMapper.toDTO(cuentaUsuarioBL.findById(id)))
                    .build();
        }
        catch (NoEncontradoException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }

    @POST
    public Response insert(CuentaUsuarioDTO cuentaUsuarioDTO) {
        try {
            CuentaUsuario cuentaUsuario = CuentaUsuarioMapper.toModelo(cuentaUsuarioDTO);
            cuentaUsuarioBL.insert(cuentaUsuario);
            cuentaUsuarioDTO = CuentaUsuarioMapper.toDTO(cuentaUsuario);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(cuentaUsuario.getId()))
                    .build();

            return Response.created(uri)
                    .entity(cuentaUsuarioDTO)
                    .build();
        }
        catch (ValidacionException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, CuentaUsuarioDTO cuentaUsuarioDTO) {
        try {
            if (id != cuentaUsuarioDTO.getId()) {
                throw new ValidacionException("El id y el payload no coinciden");
            }

            CuentaUsuario cuentaUsuario = CuentaUsuarioMapper.toModelo(cuentaUsuarioDTO);
            cuentaUsuarioBL.update(cuentaUsuario);

            return Response.ok(CuentaUsuarioMapper.toDTO(cuentaUsuario))
                    .build();
        }
        catch (NoEncontradoException ex) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
        catch (ValidacionException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id) {
        try {
            cuentaUsuarioBL.delete(id);
            return Response.noContent().build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }
}
