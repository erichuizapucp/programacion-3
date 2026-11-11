package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bl.AreaBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.error.NoEncontradoException;
import pe.edu.pucp.softprog.bl.error.ValidacionException;
import pe.edu.pucp.softprog.bl.impl.AreaBLImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.rest.dto.AreaDTO;
import pe.edu.pucp.softprog.rest.mapper.AreaMapper;

import java.net.URI;
import java.util.Map;

@Path("/areas")
@Produces("application/json")
@Consumes("application/json")
public class AreasResource {
    private final AreaBL areaBL;

    @Context
    private UriInfo uriInfo;

    public AreasResource() {
        areaBL = new AreaBLImpl();
    }

    @GET
    public Response findAll(@QueryParam("nombre") String nombre) {
        try {
            if (nombre != null && !nombre.isEmpty()) {
                return Response.ok(AreaMapper.toDTOs(areaBL.filterByName(nombre)))
                        .build();
            }
            return Response.ok(AreaMapper.toDTOs(areaBL.findAll()))
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
            return Response.ok(AreaMapper.toDTO(areaBL.findById(id)))
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
    public Response insert(AreaDTO areaDTO) {
        try {
            Area area = AreaMapper.toModelo(areaDTO);
            areaBL.insert(area);
            areaDTO = AreaMapper.toDTO(area);

            URI uri = uriInfo.getAbsolutePathBuilder()
                    .path(String.valueOf(area.getId()))
                    .build();

            return Response.created(uri)
                    .entity(areaDTO)
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
    public Response update(@PathParam("id") int id, AreaDTO areaDTO) {
        try {
            if (id != areaDTO.getId()) {
                throw new ValidacionException("El id y el payload no coinciden");
            }

            Area area = AreaMapper.toModelo(areaDTO);
            areaBL.update(area);

            return Response.ok(AreaMapper.toDTO(area))
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
            areaBL.delete(id);
            return Response.noContent().build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }
}
