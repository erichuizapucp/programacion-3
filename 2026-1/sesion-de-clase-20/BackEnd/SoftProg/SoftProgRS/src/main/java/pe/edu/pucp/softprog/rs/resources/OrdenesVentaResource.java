package pe.edu.pucp.softprog.rs.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bo.ventas.OrdenVentaBO;
import pe.edu.pucp.softprog.bo.ventas.OrdenVentaBOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/v1/ordenes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrdenesVentaResource {
    private final OrdenVentaBO ordenVentaBO;

    @Context
    private UriInfo uriInfo;

    public OrdenesVentaResource() {
        this.ordenVentaBO = new OrdenVentaBOImpl();
    }

    @GET
    public List<OrdenVenta> listar() {
        return this.ordenVentaBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") int id) {
        OrdenVenta ordenVenta = this.ordenVentaBO.obtener(id);

        if (ordenVenta == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Orden Venta: " + id + ", no encontrada"))
                    .build();
        }

        return Response.ok(ordenVenta).build();
    }

    @POST
    public Response crear(OrdenVenta ordenVenta) {
        if (ordenVenta == null || ordenVenta.getCliente() == null ||
                ordenVenta.getLineas().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La orden venta no es valida")
                    .build();
        }

        this.ordenVentaBO.guardar(ordenVenta, Estado.Nuevo);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(ordenVenta.getId()))
                .build();

        return Response.created(location)
                .entity(ordenVenta)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") int id, OrdenVenta ordenVenta) {
        if (ordenVenta == null || ordenVenta.getCliente() == null ||
                ordenVenta.getLineas().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("La orden venta no es valida")
                    .build();
        }

        if (this.ordenVentaBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Orden Venta: " + id + ", no encontrada")
                    .build();
        }

        this.ordenVentaBO.guardar(ordenVenta, Estado.Modificado);

        return Response.ok(ordenVenta).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int id) {
        if (this.ordenVentaBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Orden Venta: " + id + ", no encontrada")
                    .build();
        }
        this.ordenVentaBO.eliminar(id);

        return Response.noContent().build();
    }
}
