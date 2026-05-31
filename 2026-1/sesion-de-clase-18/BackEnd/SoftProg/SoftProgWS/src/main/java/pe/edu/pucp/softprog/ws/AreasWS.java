package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.bo.rrhh.AreaBO;
import pe.edu.pucp.softprog.bo.rrhh.AreaBOImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Area;


@WebService(
        serviceName = "AreasWS",
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class AreasWS {
    private final AreaBO areaBO;
    
    public AreasWS() {
        this.areaBO = new AreaBOImpl();
    }
    
    @WebMethod(operationName = "listarAreas")
    public List<Area> listarAreas() {
        return this.areaBO.listar();
    }
    
    @WebMethod(operationName = "obtenerArea")
    public Area obtenerArea(@WebParam(name = "id") int id) {
        return this.areaBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarArea")
    public void eliminarArea(@WebParam(name = "id") int id) {
        this.areaBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarArea")
    public void guardarArea(@WebParam(name = "area") Area area, @WebParam(name = "estado") Estado estado) {
        this.areaBO.guardar(area, estado);
    }
}