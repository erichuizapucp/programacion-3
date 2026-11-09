package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.EmpleadoBL;
import pe.edu.pucp.softprog.bl.OrdenVentaBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.impl.ClienteBLImpl;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.bl.impl.EmpleadoBLImpl;
import pe.edu.pucp.softprog.bl.impl.OrdenVentaBLImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.rest.dto.ClienteDTO;
import pe.edu.pucp.softprog.rest.dto.CuentaUsuarioDTO;
import pe.edu.pucp.softprog.rest.mapper.ClienteMapper;
import pe.edu.pucp.softprog.rest.mapper.CuentaUsuarioMapper;
import pe.edu.pucp.softprog.rest.mapper.EmpleadoMapper;
import pe.edu.pucp.softprog.rest.mapper.OrdenVentaMapper;

import java.net.URI;
import java.util.Map;

@Path("/mi-cuenta")
@Produces("application/json")
@Consumes("application/json")
public class MiCuentaResource {
    private final CuentaUsuarioBL cuentaUsuarioBL;
    private final ClienteBL clienteBL;
    private final EmpleadoBL empleadoBL;
    private final OrdenVentaBL ordenVentaBL;

    @Context
    private UriInfo uriInfo;

    public MiCuentaResource() {
        cuentaUsuarioBL = new CuentaUsuarioBLImpl();
        clienteBL = new ClienteBLImpl();
        empleadoBL = new EmpleadoBLImpl();
        ordenVentaBL = new OrdenVentaBLImpl();
    }

    @POST
    public Response registrar(ClienteDTO clienteDTO) {
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

            return Response.created(uriInfo.getAbsolutePath())
                    .entity(ClienteMapper.toDTO(cliente))
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

    @GET
    @Path("/{userName}")
    public Response find(@PathParam("userName") String userName) {
        try {
            CuentaUsuario cuentaUsuario = buscarCuenta(userName);
            return Response.ok(CuentaUsuarioMapper.toDTO(cuentaUsuario))
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

    @PUT
    @Path("/{userName}/password")
    public Response cambiarPassword(@PathParam("userName") String userName,
                                    Map<String, String> passwords) {
        try {
            CuentaUsuario cuentaUsuario = buscarCuenta(userName);
            cuentaUsuarioBL.cambiarPassword(cuentaUsuario.getId(),
                    passwords.get("passwordActual"),
                    passwords.get("passwordNueva"));
            return Response.noContent().build();
        }
        catch (ValidacionException ex) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", ex.getMessage()))
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

    @GET
    @Path("/{userName}/cliente")
    public Response findCliente(@PathParam("userName") String userName) {
        try {
            CuentaUsuario cuentaUsuario = buscarCuenta(userName);
            Cliente cliente = clienteBL.findByCuenta(cuentaUsuario.getUserName());
            if (cliente == null) {
                throw new NoEncontradoException("La cuenta de usuario no tiene un cliente asociado");
            }
            return Response.ok(ClienteMapper.toDTO(cliente))
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

    @GET
    @Path("/{userName}/empleado")
    public Response findEmpleado(@PathParam("userName") String userName) {
        try {
            CuentaUsuario cuentaUsuario = buscarCuenta(userName);
            Empleado empleado = empleadoBL.findByCuenta(cuentaUsuario.getUserName());
            if (empleado == null) {
                throw new NoEncontradoException("La cuenta de usuario no tiene un empleado asociado");
            }
            return Response.ok(EmpleadoMapper.toDTO(empleado))
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

    @GET
    @Path("/{userName}/ordenes")
    public Response findOrdenes(@PathParam("userName") String userName) {
        try {
            CuentaUsuario cuentaUsuario = buscarCuenta(userName);
            return Response.ok(OrdenVentaMapper.toDTOs(ordenVentaBL.findByCuenta(cuentaUsuario.getUserName())))
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

    private CuentaUsuario buscarCuenta(String userName) throws BLException {
        CuentaUsuario cuentaUsuario = cuentaUsuarioBL.findByUserName(userName);
        if (cuentaUsuario == null) {
            throw new NoEncontradoException("No se encontró la cuenta de usuario: " + userName);
        }
        return cuentaUsuario;
    }
}
