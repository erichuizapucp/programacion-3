package pe.edu.pucp.inf30.softprog.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.inf30.softprog.bo.ventas.OrdenVentaBO;
import pe.edu.pucp.inf30.softprog.boimpl.ventas.OrdenVentaBOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.ventas.OrdenVenta;

@Path("ordenes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OrdenVentaResource {
    private final OrdenVentaBO ordenVentaBO;
    
    public OrdenVentaResource() {
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
        URI location = URI.create("/SoftProgRest/api/v1/ordenes/" + ordenVenta.getId());
        
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
