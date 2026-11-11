package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bl.EmpleadoBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.impl.EmpleadoBLImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.rest.dto.EmpleadoDTO;
import pe.edu.pucp.softprog.rest.mapper.EmpleadoMapper;

import java.net.URI;
import java.util.Map;

@Path("/empleados")
@Produces("application/json")
@Consumes("application/json")
public class EmpleadosResource {
    private final EmpleadoBL empleadoBL;

    @Context
    private UriInfo uriInfo;

    public EmpleadosResource() {
        empleadoBL = new EmpleadoBLImpl();
    }

    @GET
    public Response findAll(@QueryParam("nombre") String nombre) {
        try {
            if (nombre != null && !nombre.isEmpty()) {
                return Response.ok(EmpleadoMapper.toDTOs(empleadoBL.filterByName(nombre)))
                        .build();
            }
            return Response.ok(EmpleadoMapper.toDTOs(empleadoBL.findAll()))
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
            return Response.ok(EmpleadoMapper.toDTO(empleadoBL.findById(id)))
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
    public Response insert(EmpleadoDTO empleadoDTO) {
        try {
            Empleado empleado = EmpleadoMapper.toModelo(empleadoDTO);
            empleadoBL.insert(empleado);
            empleadoDTO = EmpleadoMapper.toDTO(empleado);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(empleado.getId()))
                    .build();

            return Response.created(uri)
                    .entity(empleadoDTO)
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
    public Response update(@PathParam("id") int id, EmpleadoDTO empleadoDTO) {
        try {
            if (id != empleadoDTO.getId()) {
                throw new ValidacionException("El id y el payload no coinciden");
            }

            Empleado empleado = EmpleadoMapper.toModelo(empleadoDTO);
            empleadoBL.update(empleado);

            return Response.ok(EmpleadoMapper.toDTO(empleado))
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
            empleadoBL.delete(id);
            return Response.noContent().build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }
}
