package pe.edu.pucp.softprog.rs.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bo.almacen.ProductoBO;
import pe.edu.pucp.softprog.bo.almacen.ProductoBOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/v1/productos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductosResource {
    private final ProductoBO productoBO;

    @Context
    private UriInfo uriInfo;

    public ProductosResource() {
        productoBO = new ProductoBOImpl();
    }

    @GET
    public List<Producto> listaProductos() {
        return productoBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtenerProductoPorId(@PathParam("id") int idProducto) {
        if (idProducto < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El ID es inválido"))
                    .build();
        }

        Producto producto = productoBO.obtener(idProducto);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "El producto con id:  "
                            + idProducto + ", no existe"))
                    .build();
        }

        return Response.ok(producto).build();
    }

    @POST
    public Response crearProducto(Producto producto) {
        if (producto == null ||
                producto.getNombre() == null ||
                producto.getNombre().isBlank() ||
                producto.getNombre().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El payload para crear el producto es inválido"))
                    .build();
        }

        productoBO.guardar(producto, Estado.Nuevo);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(producto.getId()))
                .build();

        return Response.created(location)
                .entity(producto)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarProducto(Producto producto, @PathParam("id") int idProducto) {
        if (idProducto < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El ID es inválido"))
                    .build();
        }

        if (productoBO.obtener(idProducto) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "El area con id:  "
                            + idProducto + ", no existe"))
                    .build();
        }

        if (producto == null ||
                producto.getNombre() == null ||
                producto.getNombre().isBlank() ||
                producto.getNombre().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El payload para crear el producto es inválido"))
                    .build();
        }

        productoBO.guardar(producto, Estado.Modificado);

        return Response.ok(producto).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarProducto(@PathParam("id") int idProducto) {
        if (idProducto < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El ID es inválido"))
                    .build();
        }

        Producto producto = productoBO.obtener(idProducto);
        if (producto == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "El producto con id:  "
                            + idProducto + ", no existe"))
                    .build();
        }

        productoBO.eliminar(idProducto);

        return Response.noContent().build();
    }
}
