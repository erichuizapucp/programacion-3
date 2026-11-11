package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bl.OrdenVentaBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.impl.OrdenVentaBLImpl;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;
import pe.edu.pucp.softprog.rest.dto.OrdenVentaDTO;
import pe.edu.pucp.softprog.rest.mapper.OrdenVentaMapper;

import java.net.URI;
import java.util.Map;

@Path("/ordenes-venta")
@Produces("application/json")
@Consumes("application/json")
public class OrdenesVentaResource {
    private final OrdenVentaBL ordenVentaBL;

    @Context
    private UriInfo uriInfo;

    public OrdenesVentaResource() {
        ordenVentaBL = new OrdenVentaBLImpl();
    }

    @GET
    public Response findAll() {
        try {
            return Response.ok(OrdenVentaMapper.toDTOs(ordenVentaBL.findAll()))
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
            return Response.ok(OrdenVentaMapper.toDTO(ordenVentaBL.findById(id)))
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
    public Response insert(OrdenVentaDTO ordenVentaDTO) {
        try {
            OrdenVenta ordenVenta = OrdenVentaMapper.toModelo(ordenVentaDTO);
            ordenVentaBL.insert(ordenVenta);
            ordenVentaDTO = OrdenVentaMapper.toDTO(ordenVenta);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(ordenVenta.getId()))
                    .build();

            return Response.created(uri)
                    .entity(ordenVentaDTO)
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
    public Response update(@PathParam("id") int id, OrdenVentaDTO ordenVentaDTO) {
        try {
            if (id != ordenVentaDTO.getId()) {
                throw new ValidacionException("El id y el payload no coinciden");
            }

            OrdenVenta ordenVenta = OrdenVentaMapper.toModelo(ordenVentaDTO);
            ordenVentaBL.update(ordenVenta);

            return Response.ok(OrdenVentaMapper.toDTO(ordenVenta))
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
            ordenVentaBL.delete(id);
            return Response.noContent().build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }
}
