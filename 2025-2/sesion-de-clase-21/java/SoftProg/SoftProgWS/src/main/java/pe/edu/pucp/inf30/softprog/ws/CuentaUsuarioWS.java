package pe.edu.pucp.inf30.softprog.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
@WebService(serviceName = "CuentaUsuarioWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class CuentaUsuarioWS {
    private final ResourceBundle config;
    private final String urlBase;
    private final HttpClient client = HttpClient.newHttpClient();
    private final String NOMBRE_RESOURCE = "cuentas";
    
    public CuentaUsuarioWS() {
        this.config = ResourceBundle.getBundle("app");
        this.urlBase = this.config.getString("app.services.rest.baseurl");
    }
    
    @WebMethod(operationName = "listarCuentasUsuario")
    public List<CuentaUsuario> listarCuentasUsuario() 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        ObjectMapper mapper= new ObjectMapper();
        List<CuentaUsuario> cuentas = 
                mapper.readValue(json, new TypeReference<List<CuentaUsuario>>() {});
        
        return cuentas;
    }
    
    @WebMethod(operationName = "obtenerCuentaUsuario")
    public CuentaUsuario obtenerCuentaUsuario(@WebParam(name = "id") int id) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        ObjectMapper mapper= new ObjectMapper();
        CuentaUsuario cuenta = mapper.readValue(json, CuentaUsuario.class);
        
        return cuenta;
    }
    
    @WebMethod(operationName = "eliminarCuentaUsuario")
    public void eliminarCuentaUsuario(@WebParam(name = "id") int id) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    @WebMethod(operationName = "guardarCuentaUsuario")
    public void guardarCuentaUsuario(@WebParam(name = "area") CuentaUsuario cuentaUsuario, 
                                     @WebParam(name = "estado") Estado estado) 
            throws IOException, InterruptedException {
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cuentaUsuario);

        String url;
        HttpRequest request;

        if (estado == Estado.Nuevo) {
            url = this.urlBase + "/" + this.NOMBRE_RESOURCE;
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        } else {
            url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + cuentaUsuario.getId();
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    @WebMethod(operationName = "login")
    public boolean login(@WebParam(name = "userName") String userName, 
                         @WebParam(name = "password") String password) 
            throws IOException, InterruptedException {
        
        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName(userName);
        cuenta.setPassword(password);
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(cuenta);
        
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/login";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        
        return response.statusCode() == 200;
    }
}
