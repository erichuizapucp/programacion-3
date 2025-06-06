package pe.edu.pucp.softprog.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import pe.edu.pucp.softprog.model.Area;

/**
 *
 * @author eric
 */
public class PrincipalObtenerTodas {
    public static void main(String[] args) throws Exception {
        String url = "http://localhost:8080/WebApplication/api/areas";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        
        HttpResponse<String> response = 
                client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();
        ObjectMapper mapper= new ObjectMapper();
        List<Area> areas = mapper.readValue(json, 
                new TypeReference<List<Area>>() {});
        
        for (Area area : areas) {
            System.out.println("Id: " + area.getId());
            System.out.println("Nombre: " + area.getNombre());
            System.out.println("Activa: " + area.isActiva());
            System.out.println();
        }
    }
}
