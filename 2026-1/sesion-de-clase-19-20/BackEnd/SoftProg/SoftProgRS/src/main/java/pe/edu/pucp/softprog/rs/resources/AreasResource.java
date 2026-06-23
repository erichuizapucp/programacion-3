package pe.edu.pucp.softprog.rs.resources;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import pe.edu.pucp.softprog.bo.rrhh.AreaBO;
import pe.edu.pucp.softprog.bo.rrhh.AreaBOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.net.URI;
import java.util.List;
import java.util.Map;

@Path("/v1/areas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AreasResource {
    private final AreaBO areaBO;

    @Context
    private UriInfo uriInfo;

    public AreasResource() {
        areaBO = new AreaBOImpl();
    }

    @GET
    public List<Area> listaAreas() {
        return areaBO.listar();
    }

    @GET
    @Path("{id}")
    public Response obtenerAreaPorId(@PathParam("id") int idArea) {
        if (idArea < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El ID es inválido"))
                    .build();
        }

        Area area = areaBO.obtener(idArea);
        if (area == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "El area con id:  "
                            + idArea + ", no existe"))
                    .build();
        }

        return Response.ok(area).build();
    }

    @POST
    public Response crearArea(Area area) {
        if (area == null ||
                area.getNombre() == null ||
                area.getNombre().isBlank() ||
                area.getNombre().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El payload para crear el área es inválido"))
                    .build();
        }

        areaBO.guardar(area, Estado.Nuevo);
        URI location = uriInfo.getAbsolutePathBuilder()
                .path(String.valueOf(area.getId()))
                .build();

        return Response.created(location)
                .entity(area)
                .build();
    }

    @PUT
    @Path("{id}")
    public Response actualizarArea(Area area, @PathParam("id") int idArea) {
        if (idArea < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El ID es inválido"))
                    .build();
        }

        if (areaBO.obtener(idArea) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "El area con id:  "
                            + idArea + ", no existe"))
                    .build();
        }

        if (area == null ||
                area.getNombre() == null ||
                area.getNombre().isBlank() ||
                area.getNombre().isEmpty()) {

            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El payload para crear el área es inválido"))
                    .build();
        }

        areaBO.guardar(area, Estado.Modificado);

        return Response.ok(area).build();
    }

    @DELETE
    @Path("{id}")
    public Response eliminarArea(@PathParam("id") int idArea) {
        if (idArea < 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El ID es inválido"))
                    .build();
        }

        Area area = areaBO.obtener(idArea);
        if (area == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "El area con id:  "
                            + idArea + ", no existe"))
                    .build();
        }

        areaBO.eliminar(idArea);

        return Response.noContent().build();
    }
}
