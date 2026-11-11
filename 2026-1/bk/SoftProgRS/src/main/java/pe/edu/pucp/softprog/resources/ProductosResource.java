package pe.edu.pucp.softprog.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.net.URI;
import java.net.URL;
import java.util.List;

import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softprog.bo.almacen.ProductoBO;
import pe.edu.pucp.softprog.bo.almacen.ProductoBOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.almacen.Producto;

@Path("/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductosResource {
    private final ProductoBO productoBO;

    public ProductosResource() {
        productoBO = new ProductoBOImpl();
    }

    @GET
    public List<Producto> getProductos() {
        return productoBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtenerProducto(@PathParam("id") int id) {
        Producto producto = productoBO.obtener(id);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("No existe el producto con el id: " + id)
                    .build();
        }

        return Response.ok(producto).build();
    }

    @POST
    public Response crearProducto(Producto producto) {
        if (producto == null || producto.getNombre().isBlank() || producto.getNombre().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        this.productoBO.guardar(producto, Estado.Nuevo);
        URI location = URI.create("/SoftProgRS-1.0-SNAPSHOT/api/productos/" + producto.getId());

        return Response.created(location).entity(producto).build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarProducto(@PathParam("id") int id, Producto producto) {
        if (producto == null || producto.getNombre().isBlank() || producto.getNombre().isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El producto es inválido")
                    .build();
        }

        if (productoBO.obtener(id) == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No existe el producto con el id: " + id)
                    .build();
        }

        producto.setId(id);
        productoBO.guardar(producto, Estado.Modificado);

        return Response.ok(producto).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarProducto(@PathParam("id") int id) {
        if (id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("No existe el producto con el id: " + id)
                    .build();
        }

        this.productoBO.eliminar(id);
        return Response.noContent().build();
    }
}
