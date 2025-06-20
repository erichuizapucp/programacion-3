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
import pe.edu.pucp.inf30.softprog.bo.IEmpleadoBO;
import pe.edu.pucp.inf30.softprog.boimpl.EmpleadoBOImpl;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.rrhh.Empleado;

/**
 *
 * @author eric
 */
@Path("empleados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmpleadoResource {
    private final IEmpleadoBO empleadoBO;
    
    public EmpleadoResource() {
        this.empleadoBO = new EmpleadoBOImpl();
    }
    
    @GET
    public List<Empleado> listar() {
        return this.empleadoBO.listar();
    }
    
    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") int id) {
        Empleado empleado = this.empleadoBO.obtener(id);
        
        if (empleado == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Empleado: " + id + ", no encontrado"))
                    .build();
        }
        
        return Response.ok(empleado).build();
    }
    
    @GET
    @Path("dni/{dni}")
    public Response obtenerPorDni(@PathParam("dni") String dni) {
        Empleado empleado = 
                this.empleadoBO.buscarPorDni(dni);
        
        if (empleado == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Empleado: con dni = " + dni + ", no encontrado"))
                    .build();
        }
        
        return Response.ok(empleado).build();
    }
    
    @POST
    public Response crear(Empleado empleado) {
        if (empleado == null || empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El empleado no es valido")
                    .build();
        }
        
        this.empleadoBO.guardar(empleado, Estado.Nuevo);
        URI location = URI.create("/SoftProgRest/api/empleados/" + empleado.getId());
        
        return Response.created(location)
                .entity(empleado)
                .build();
    }
    
    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") int id, Empleado empleado) {
        if (empleado == null || empleado.getNombre() == null || empleado.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El empleado no es valido"))
                    .build();
        }
        
        if (this.empleadoBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Empleado: " + id + ", no encontrado")
                    .build();
        }
        
        this.empleadoBO.guardar(empleado, Estado.Modificar);
        
        return Response.ok(empleado).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int id) {
        if (this.empleadoBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Empleado: " + id + ", no encontrada")
                    .build();
        }
        this.empleadoBO.eliminar(id);
        
        return Response.noContent().build();
    }
}
