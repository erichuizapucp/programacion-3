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
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;

/**
 *
 * @author eric
 */
@WebService(
        serviceName = "AreaWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class AreaWS {
    private final ResourceBundle config;
    private final String urlBase;
    private final HttpClient client = HttpClient.newHttpClient();
    private final String NOMBRE_RESOURCE = "areas";
    
    public AreaWS() {
        this.config = ResourceBundle.getBundle("app");
        this.urlBase = this.config.getString("app.services.rest.baseurl");
    }
    
    @WebMethod(operationName = "listarAreas")
    public List<Area> listarAreas()
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
        List<Area> productos = 
                mapper.readValue(json, new TypeReference<List<Area>>() {});
        
        return productos;
    }
    
    @WebMethod(operationName = "obtenerArea")
    public Area obtenerArea(@WebParam(name = "id") int id)
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
        Area area = mapper.readValue(json, Area.class);
        
        return area;
    }
    
    @WebMethod(operationName = "eliminarArea")
    public void eliminarArea(@WebParam(name = "id") int id) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    @WebMethod(operationName = "guardarArea")
    public void guardarArea(@WebParam(name = "area") Area area, 
                            @WebParam(name = "estado") Estado estado)
        throws IOException, InterruptedException {
        
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(area);

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
            url = this.urlBase + "/" + this.NOMBRE_RESOURCE + "/" + area.getId();
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}