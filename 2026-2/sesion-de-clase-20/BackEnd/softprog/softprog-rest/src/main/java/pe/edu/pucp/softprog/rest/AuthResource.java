package pe.edu.pucp.softprog.rest;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Response;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.bl.error.BLException;
import pe.edu.pucp.softprog.bl.impl.CuentaUsuarioBLImpl;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.util.Map;

@Path("/login")
@Produces("application/json")
@Consumes("application/json")
public class AuthResource {
    private final CuentaUsuarioBL cuentaUsuarioBL;

    public AuthResource() {
        cuentaUsuarioBL = new CuentaUsuarioBLImpl();
    }

    @POST
    public Response login(Map<String, String> credenciales) {
        try {
            String userName = credenciales.get("userName");
            String password = credenciales.get("password");
            if (!cuentaUsuarioBL.autenticar(userName, password)) {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity(Map.of("error", "Usuario o contraseña incorrectos"))
                        .build();
            }
            CuentaUsuario cuentaUsuario = cuentaUsuarioBL.findByUserName(userName);
            return Response.ok(Map.of("id", cuentaUsuario.getId(), "userName", cuentaUsuario.getUserName()))
                    .build();
        }
        catch (BLException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(Map.of("error", ex.getMessage()))
                    .build();
        }
    }
}
