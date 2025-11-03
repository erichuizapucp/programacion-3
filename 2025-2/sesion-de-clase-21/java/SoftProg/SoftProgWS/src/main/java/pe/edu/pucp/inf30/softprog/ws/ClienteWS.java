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
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TimeZone;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;

/**
 *
 * @author eric
 */
@WebService(serviceName = "ClienteWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class ClienteWS {
    private final ResourceBundle config;
    private final String urlBase;
    private final HttpClient client = HttpClient.newHttpClient();
    private final String NOMBRE_RESOURCE = "clientes";
    private final ObjectMapper mapper;
    
    public ClienteWS() {
        this.config = ResourceBundle.getBundle("app");
        this.urlBase = this.config.getString("app.services.rest.baseurl");
        
        this.mapper= new ObjectMapper();
        SimpleDateFormat df = 
                new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.mapper.setDateFormat(df);
    }
    
    @WebMethod(operationName = "listarClientes")
    public List<Cliente> listarClientes() 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        List<Cliente> clientes = 
                mapper.readValue(json, new TypeReference<List<Cliente>>() {});
        
        return clientes;
    }
    
    @WebMethod(operationName = "obtenerCliente")
    public Cliente obtenerCliente(@WebParam(name = "id") int id) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Cliente cliente = mapper.readValue(json, Cliente.class);
        return cliente;
    }
    
    @WebMethod(operationName = "eliminarCliente")
    public void eliminarCliente(@WebParam(name = "id") int id) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    @WebMethod(operationName = "guardarCliente")
    public void guardarCliente(@WebParam(name = "cliente") Cliente cliente, 
                               @WebParam(name = "estado") Estado estado) 
            throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(cliente);

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
            url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + cliente.getId();
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    @WebMethod(operationName = "buscarClientePorDni")
    public Cliente buscarClientePorDni(@WebParam(name = "dni") String dni) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/dni/" + dni;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Cliente cliente = mapper.readValue(json, Cliente.class);
        
        return cliente;
    }
    
    @WebMethod(operationName = "buscarClientePorCuenta")
    public Cliente buscarClientePorCuenta(@WebParam(name = "cuenta") String cuenta)
            throws IOException, InterruptedException{
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/cuenta/" + cuenta;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        Cliente cliente = mapper.readValue(json, Cliente.class);
        
        return cliente;
    }
}
