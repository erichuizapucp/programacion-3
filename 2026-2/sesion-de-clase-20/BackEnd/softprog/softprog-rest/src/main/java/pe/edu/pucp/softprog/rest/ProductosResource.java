package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.ProductoBL;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.impl.ProductoBLImpl;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.rest.dto.ProductoDTO;
import pe.edu.pucp.softprog.rest.mapper.ProductoMapper;

import java.net.URI;
import java.util.Map;

@Path("/productos")
@Produces("application/json")
@Consumes("application/json")
public class ProductosResource {
    private final ProductoBL productoBL;

    @Context
    private UriInfo uriInfo;

    public ProductosResource() {
        productoBL = new ProductoBLImpl();
    }

    @GET
    public Response findAll(@QueryParam("nombre") String nombre) {
        try {
            if (nombre != null && !nombre.isEmpty()) {
                return Response.ok(ProductoMapper.toDTOs(productoBL.filterByName(nombre)))
                        .build();
            }
            return Response.ok(ProductoMapper.toDTOs(productoBL.findAll()))
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
            return Response.ok(ProductoMapper.toDTO(productoBL.findById(id)))
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
    public Response insert(ProductoDTO productoDTO) {
        try {
            Producto producto = ProductoMapper.toModelo(productoDTO);
            productoBL.insert(producto);
            productoDTO = ProductoMapper.toDTO(producto);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(producto.getId()))
                    .build();

            return Response.created(uri)
                    .entity(productoDTO)
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
    public Response update(@PathParam("id") int id, ProductoDTO productoDTO) {
        try {
            if (id != productoDTO.getId()) {
                throw new ValidacionException("El id y el payload no coinciden");
            }

            Producto producto = ProductoMapper.toModelo(productoDTO);
            productoBL.update(producto);
            productoDTO = ProductoMapper.toDTO(producto);

            return Response.ok(productoDTO)
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
            productoBL.delete(id);
            return Response.noContent().build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }
}