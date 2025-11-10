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
import pe.edu.pucp.inf30.softprog.modelo.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
@WebService(serviceName = "OrdenVentaWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class OrdenVentaWS {
    private final ResourceBundle config;
    private final String urlBase;
    private final HttpClient client = HttpClient.newHttpClient();
    private final String NOMBRE_RECURSO = "ordenes";
    private final ObjectMapper mapper;
    
    public OrdenVentaWS() {
        this.config = ResourceBundle.getBundle("app");
        this.urlBase = this.config.getString("app.services.rest.baseurl");
        
        this.mapper= new ObjectMapper();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.mapper.setDateFormat(df);
    }
    
    @WebMethod(operationName = "listarOrdenesVenta")
    public List<OrdenVenta> listarOrdenesVenta() 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RECURSO;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        List<OrdenVenta> ordenes = 
                mapper.readValue(json, new TypeReference<List<OrdenVenta>>() {});
        
        return ordenes;
    }
    
    @WebMethod(operationName = "obtenerOrdenVenta")
    public OrdenVenta obtenerOrdenVenta(@WebParam(name = "id") int id) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RECURSO + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        OrdenVenta ordenVenta = mapper.readValue(json, OrdenVenta.class);
        
        return ordenVenta;
    }
    
    @WebMethod(operationName = "eliminarOrdenVenta")
    public void eliminarOrdenVenta(@WebParam(name = "id") int id) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/" + this.NOMBRE_RECURSO + "/" + id;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .DELETE()
                .build();
        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    @WebMethod(operationName = "guardarOrdenVenta")
    public void guardarOrdenVenta(@WebParam(name = "ordenventa") OrdenVenta ordenVenta, 
                                  @WebParam(name = "estado") Estado estado) 
            throws IOException, InterruptedException {
        String json = mapper.writeValueAsString(ordenVenta);

        String url;
        HttpRequest request;

        if (estado == Estado.Nuevo) {
            url = this.urlBase + "/" + this.NOMBRE_RECURSO;
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        } else {
            url = this.urlBase + "/" + this.NOMBRE_RECURSO + "/" + ordenVenta.getId();
            request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(json))
                    .build();
        }

        client.send(request, HttpResponse.BodyHandlers.ofString());
    }
    
    @WebMethod(operationName = "listarOrdenesVentaPorCuenta")
    public List<OrdenVenta> listarOrdenesVentaPorCuenta(String cuenta) 
            throws IOException, InterruptedException {
        String url = this.urlBase + "/cuentas/" + cuenta + "/ordenesventa";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        List<OrdenVenta> ordenes = 
                mapper.readValue(json, new TypeReference<List<OrdenVenta>>() {});
        
        return ordenes;
    }
}
