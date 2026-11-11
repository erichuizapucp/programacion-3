package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.impl.ClienteBLImpl;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.rest.dto.ClienteDTO;
import pe.edu.pucp.softprog.rest.dto.CuentaUsuarioDTO;
import pe.edu.pucp.softprog.rest.mapper.ClienteMapper;
import pe.edu.pucp.softprog.rest.mapper.CuentaUsuarioMapper;

import java.net.URI;
import java.util.Map;

@Path("/clientes")
@Produces("application/json")
@Consumes("application/json")
public class ClientesResource {
    private final ClienteBL clienteBL;

    @Context
    private UriInfo uriInfo;

    public ClientesResource() {
        clienteBL = new ClienteBLImpl();
    }

    @GET
    public Response findAll(@QueryParam("nombre") String nombre) {
        try {
            if (nombre != null && !nombre.isEmpty()) {
                return Response.ok(ClienteMapper.toDTOs(clienteBL.filterByName(nombre)))
                        .build();
            }
            return Response.ok(ClienteMapper.toDTOs(clienteBL.findAll()))
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
            return Response.ok(ClienteMapper.toDTO(clienteBL.findById(id)))
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
    public Response insert(ClienteDTO clienteDTO) {
        try {
            CuentaUsuarioDTO cuentaDTO = clienteDTO.getCuentaUsuario();
            if (cuentaDTO == null) {
                throw new ValidacionException("El cliente debe tener una cuenta de usuario");
            }
            clienteDTO.setCuentaUsuario(null);
            if (clienteDTO.getCategoria() == null) {
                clienteDTO.setCategoria(CategoriaCliente.BRONCE.name());
            }

            Cliente cliente = ClienteMapper.toModelo(clienteDTO);
            CuentaUsuario cuentaUsuario = CuentaUsuarioMapper.toModelo(cuentaDTO);
            cuentaUsuario.setActivo(true);
            cliente.setCuentaUsuario(cuentaUsuario);
            clienteBL.registrar(cliente);
            clienteDTO = ClienteMapper.toDTO(cliente);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(cliente.getId()))
                    .build();

            return Response.created(uri)
                    .entity(clienteDTO)
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
    public Response update(@PathParam("id") int id, ClienteDTO clienteDTO) {
        try {
            if (id != clienteDTO.getId()) {
                throw new ValidacionException("El id y el payload no coinciden");
            }

            Cliente cliente = ClienteMapper.toModelo(clienteDTO);
            clienteBL.update(cliente);

            return Response.ok(ClienteMapper.toDTO(cliente))
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
            clienteBL.delete(id);
            return Response.noContent().build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }
}
