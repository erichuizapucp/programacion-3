package pe.edu.pucp.softprog.resources;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;
import pe.edu.pucp.softprog.modelo.Usuario;

/**
 *
 * @author eric
 */
@Path("usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuariosResource {
    @GET
    public List<Usuario> obtenerUsuarios() {
        Usuario u1 = new Usuario();
        u1.setCodigo(12345);
        u1.setNombre("Juan Perez");
        
        Usuario u2 = new Usuario();
        u2.setCodigo(6789);
        u2.setNombre("David Gomez");
        
        List<Usuario> usuarios = List.of(u1, u2);
        return usuarios;
    }
}
