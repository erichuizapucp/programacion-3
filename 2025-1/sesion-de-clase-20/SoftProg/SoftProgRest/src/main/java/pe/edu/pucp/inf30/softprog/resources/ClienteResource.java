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
import pe.edu.pucp.inf30.softprog.bo.IClienteBO;
import pe.edu.pucp.inf30.softprog.boimpl.ClienteBOImpl;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.model.rrhh.Empleado;

/**
 *
 * @author eric
 */
@Path("clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteResource {
    private final IClienteBO clienteBO;
    
    public ClienteResource() {
        this.clienteBO = new ClienteBOImpl();
    }
    
    @GET
    public List<Cliente> listar() {
        return this.clienteBO.listar();
    }
    
    @GET
    @Path("{id}")
    public Response obtener(@PathParam("id") int id) {
        Cliente cliente = this.clienteBO.obtener(id);
        
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Cliente: " + id + ", no encontrado"))
                    .build();
        }
        
        return Response.ok(cliente).build();
    }
    
    @GET
    @Path("dni/{dni}")
    public Response obtenerPorDni(@PathParam("dni") String dni) {
        Cliente cliente = this.clienteBO.buscarPorDni(dni);
        
        if (cliente == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(Map.of("error", "Cliente: con dni = " + dni + ", no encontrado"))
                    .build();
        }
        
        return Response.ok(cliente).build();
    }
    
    @POST
    public Response crear(Cliente cliente) {
        if (cliente == null || cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("El cliente no es valido")
                    .build();
        }
        
        this.clienteBO.guardar(cliente, Estado.Nuevo);
        URI location = URI.create("/SoftProgRest/api/clientes/" + cliente.getId());
        
        return Response.created(location)
                .entity(cliente)
                .build();
    }
    
    @PUT
    @Path("{id}")
    public Response actualizar(@PathParam("id") int id, Cliente cliente) {
        if (cliente == null || cliente.getNombre() == null || cliente.getNombre().isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(Map.of("error", "El cliente no es valido"))
                    .build();
        }
        
        if (this.clienteBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("cliente: " + id + ", no encontrado")
                    .build();
        }
        
        this.clienteBO.guardar(cliente, Estado.Modificar);
        
        return Response.ok(cliente).build();
    }
    
    @DELETE
    @Path("{id}")
    public Response eliminar(@PathParam("id") int id) {
        if (this.clienteBO.obtener(id) == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Cliente: " + id + ", no encontrada")
                    .build();
        }
        this.clienteBO.eliminar(id);
        
        return Response.noContent().build();
    }
}
