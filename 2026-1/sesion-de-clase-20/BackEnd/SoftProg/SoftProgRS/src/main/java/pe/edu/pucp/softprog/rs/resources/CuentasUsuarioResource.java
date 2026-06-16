package pe.edu.pucp.softprog.rs.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bo.cuentas.CuentaUsuarioBO;
import pe.edu.pucp.softprog.bo.cuentas.CuentaUsuarioBOImpl;
import pe.edu.pucp.softprog.bo.ventas.OrdenVentaBO;
import pe.edu.pucp.softprog.bo.ventas.OrdenVentaBOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/v1/cuentas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CuentasUsuarioResource {
    private final CuentaUsuarioBO cuentaUsuarioBO;
    private final OrdenVentaBO ordenVentaBO;

    @Context
    private UriInfo uriInfo;

    public CuentasUsuarioResource() {
        this.cuentaUsuarioBO = new CuentaUsuarioBOImpl();
        this.ordenVentaBO = new OrdenVentaBOImpl();
    }

    @GET
    public List<CuentaUsuario> listar() {
        return this.cuentaUsuarioBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") int id) {
        CuentaUsuario cuenta = this.cuentaUsuarioBO.obtener(id);

        if (cuenta == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Area: " + id + ", no encontrada"))
                    .build();
        }

        return Response.ok(cuenta).build();
    }

    @POST
    public Response crear(CuentaUsuario cuenta) {
        if (cuenta == null || cuenta.getUserName()== null ||
                cuenta.getUserName().isBlank() ||
                cuenta.getPassword() == null ||
                cuenta.getPassword().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La cuenta no es valida")
                    .build();
        }

        this.cuentaUsuarioBO.guardar(cuenta, Estado.Nuevo);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(cuenta.getId()))
                .build();

        return Response.created(location)
                .entity(cuenta)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") int id, CuentaUsuario cuenta) {
        if (cuenta == null || cuenta.getUserName()== null ||
                cuenta.getUserName().isBlank() ||
                cuenta.getPassword() == null ||
                cuenta.getPassword().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La cuenta no es valida")
                    .build();
        }

        if (this.cuentaUsuarioBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cuenta: " + id + ", no encontrada")
                    .build();
        }

        this.cuentaUsuarioBO.guardar(cuenta, Estado.Modificado);

        return Response.ok(cuenta).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int id) {
        if (this.cuentaUsuarioBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cuenta: " + id + ", no encontrada")
                    .build();
        }
        this.cuentaUsuarioBO.eliminar(id);

        return Response.noContent().build();
    }

    @POST
    @Path("login")
    public Response login(CuentaUsuario cuenta) {
        boolean success =
                this.cuentaUsuarioBO.login(
                        cuenta.getUserName(),
                        cuenta.getPassword());

        if (success) {
            return Response.status(Response.Status.OK)
                    .entity("Login exitoso")
                    .build();
        }

        return Response.status(401)
                .entity("Usuario o password incorrectos")
                .build();
    }

    @GET
    @Path("{cuenta}/ordenes")
    public Response listarPorCuenta(@PathParam("cuenta") String cuenta) {
        List<OrdenVenta> ordenes =
                this.ordenVentaBO.listarOrdenesVentaPorCuenta(cuenta);

        if (ordenes == null || ordenes.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La cuenta no es valida")
                    .build();
        }

        return Response.ok(ordenes).build();
    }
}
