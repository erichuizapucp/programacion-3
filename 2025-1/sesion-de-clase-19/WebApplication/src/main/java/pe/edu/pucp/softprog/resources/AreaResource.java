package pe.edu.pucp.softprog.resources;

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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pe.edu.pucp.softprog.model.Area;

/**
 *
 * @author eric
 */
@Path("areas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AreaResource {
    private static final List<Area> areas = new ArrayList<>();
    private static int inc = 1;

    static {
        areas.add(new Area() {{
            setId(inc++);
            setNombre("SISTEMAS");
            setActiva(true);
        }});
        areas.add(new Area() {{
            setId(inc++);
            setNombre("CONTABILIDAD");
            setActiva(true);
        }});
    }
    
    @GET
    public List<Area> obtenerAreas() {
        return areas;
    }
    
    @GET
    @Path("{id}")
    public Response obtenerArea(@PathParam("id") int id) {
        return areas.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .map(area -> Response.ok(area).build())
            .orElse(Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Area: " + id + ", no encontrada"))
                    .build());
    }
    
    @POST
    public Response crearArea(Area area) {
        if (area == null || area.getNombre() == null 
                || area.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El area no es valida")
                    .build();
        }
        
        area.setId(inc++);
        areas.add(area);
        
        URI location = URI.create("/WebApplication/api/areas/" + area.getId());
        
        return Response.created(location)
                .entity(area)
                .build();
    }
    
    @PUT
    @Path("{id}")
    public Response actualizarArea(@PathParam("id") int id, Area area) {
        if (area == null || 
            area.getNombre() == null || area.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El area no es valida"))
                    .build();
        }
        
        return areas.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .map(a -> {
                area.setId(id);
                areas.set(area.getId() - 1, area);
                return Response.ok(area).build();
            })
            .orElse(Response.status(Response.Status.NOT_FOUND)
                    .entity("Area: " + id + ", no encontrada")
                    .build());
    }
    
    @DELETE
    @Path("{id}")
    public Response eliminarArea(@PathParam("id") int id) {
        return areas.stream()
            .filter(a -> a.getId() == id)
            .findFirst()
            .map(area -> {
                areas.remove(id - 1);
                return Response.noContent().build();
            })
            .orElse(Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Area: " + id + ", no encontrada"))
                    .build());
    }
}
