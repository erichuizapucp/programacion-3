package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.IAreaBO;
import pe.edu.pucp.inf30.softprog.boimpl.AreaBOImpl;

import pe.edu.pucp.inf30.softprog.model.rrhh.Area;

/**
 *
 * @author eric
 */
@WebService(
        serviceName = "AreaWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class AreaWS {
    private final IAreaBO areaBO;
    
    public AreaWS() {
        this.areaBO = new AreaBOImpl();
    }
    
    @WebMethod(operationName = "listarAreas")
    public List<Area> listarAreas() {
        return this.areaBO.listar();
    }
    
    @WebMethod(operationName = "obtenerArea")
    public Area obtenerArea(
        @WebParam(name = "id") int id
    ) {
        return this.areaBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarArea")
    public void eliminarArea(
        @WebParam(name = "id") int id
    ) {
        this.areaBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarArea")
    public void guardarArea(
        @WebParam(name = "area") Area area, 
        @WebParam(name = "estado") Estado estado
    ) {
        this.areaBO.guardar(area, estado);
    }
}