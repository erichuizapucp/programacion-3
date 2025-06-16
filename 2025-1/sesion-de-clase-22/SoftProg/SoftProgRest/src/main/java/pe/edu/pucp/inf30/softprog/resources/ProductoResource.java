package pe.edu.pucp.inf30.softprog.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.List;
import java.util.Map;
import pe.edu.pucp.inf30.softprog.bo.IProductoBO;
import pe.edu.pucp.inf30.softprog.boimpl.ProductoBOImpl;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.logistica.almacen.Producto;

/**
 *
 * @author eric
 */
@Path("productos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProductoResource {
    private final IProductoBO productoBO;
    
    public ProductoResource() {
        this.productoBO = new ProductoBOImpl();
    }
    
    @GET
    public List<Producto> listar() {
        return this.productoBO.listar();
    }
    
    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") int id) {
        Producto producto = this.productoBO.obtener(id);
        
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Producto: " + id + ", no encontrado"))
                    .build();
        }
        
        return Response.ok(producto).build();
    }
    
    @POST
    public Response crear(Producto producto) {
        if (producto == null || producto.getNombre() == null || producto.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El producto no es valido")
                    .build();
        }
        
        this.productoBO.guardar(producto, Estado.Nuevo);
        URI location = URI.create("/SoftProgRest/api/productos/" + producto.getId());
        
        return Response.created(location)
                .entity(producto)
                .build();
    }
    
    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") int id, Producto producto) {
        if (producto == null || producto.getNombre() == null || producto.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El producto no es valido"))
                    .build();
        }
        
        if (this.productoBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Producto: " + id + ", no encontrado")
                    .build();
        }
        
        this.productoBO.guardar(producto, Estado.Modificar);
        
        return Response.ok(producto).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int id) {
        if (this.productoBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Producto: " + id + ", no encontrada")
                    .build();
        }
        this.productoBO.eliminar(id);
        
        return Response.noContent().build();
    }
}
