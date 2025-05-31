package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.IAreaBO;
import pe.edu.pucp.inf30.softprog.boimpl.AreaBOImpl;

import pe.edu.pucp.inf30.softprog.rrhh.model.Area;

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
    
    @WebMethod(operationName = "listar")
    public List<Area> listar() {
        return this.areaBO.listar();
    }
    
    @WebMethod(operationName = "obtener")
    public Area obtener(
        @WebParam(name = "id") int id
    ) {
        return this.areaBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminar")
    public void eliminar(
        @WebParam(name = "id") int id
    ) {
        this.areaBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardar")
    public void guardar(
        @WebParam(name = "area") Area area, 
        @WebParam(name = "estado") Estado estado
    ) {
        this.areaBO.guardar(area, estado);
    }
}

//List<T> listar();
//T obtener(int id);
//void eliminar(int id);
//void guardar(T modelo, Estado estado);