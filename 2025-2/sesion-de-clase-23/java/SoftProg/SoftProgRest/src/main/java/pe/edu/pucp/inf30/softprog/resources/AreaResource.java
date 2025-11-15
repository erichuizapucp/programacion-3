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
import pe.edu.pucp.inf30.softprog.bo.rrhh.AreaBO;
import pe.edu.pucp.inf30.softprog.boimpl.rrhh.AreaBOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;

/**
 *
 * @author eric
 */
@Path("areas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AreaResource {
    private final AreaBO areaBO;
    
    public AreaResource() {
        this.areaBO = new AreaBOImpl();
    }
    
    @GET
    public List<Area> listar() {
        return this.areaBO.listar();
    }
    
    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") int id) {
        Area area = this.areaBO.obtener(id);
        
        if (area == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Area: " + id + ", no encontrada"))
                    .build();
        }
        
        return Response.ok(area).build();
    }
    
    @POST
    public Response crear(Area area) {
        if (area == null || area.getNombre() == null || area.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El area no es valida")
                    .build();
        }
        
        this.areaBO.guardar(area, Estado.Nuevo);
        URI location = 
                URI.create("/SoftProgRest/api/v1/areas/" + area.getId());
        
        return Response.created(location)
                .entity(area)
                .build();
    }
    
    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") int id, Area area) {
        if (area == null || area.getNombre() == null || area.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El area no es valida"))
                    .build();
        }
        
        if (this.areaBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Area: " + id + ", no encontrada")
                    .build();
        }
        
        this.areaBO.guardar(area, Estado.Modificado);
        
        return Response.ok(area).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int id) {
        if (this.areaBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Area: " + id + ", no encontrada")
                    .build();
        }
        this.areaBO.eliminar(id);
        
        return Response.noContent().build();
    }
}
