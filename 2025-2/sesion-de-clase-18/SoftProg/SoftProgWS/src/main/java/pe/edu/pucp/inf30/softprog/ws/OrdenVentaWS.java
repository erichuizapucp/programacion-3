package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.IOrdenVentaBO;
import pe.edu.pucp.inf30.softprog.boimpl.OrdenVentaBOImpl;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
@WebService(serviceName = "OrdenVentaWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class OrdenVentaWS {
    private final IOrdenVentaBO ordenVentaBO;
    
    public OrdenVentaWS() {
        this.ordenVentaBO = new OrdenVentaBOImpl();
    }
    
    @WebMethod(operationName = "listarOrdenesVenta")
    public List<OrdenVenta> listarOrdenesVenta() {
        return this.ordenVentaBO.listar();
    }
    
    @WebMethod(operationName = "obtenerOrdenVenta")
    public OrdenVenta obtenerOrdenVenta(
        @WebParam(name = "id") int id
    ) {
        return this.ordenVentaBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarOrdenVenta")
    public void eliminarOrdenVenta(
        @WebParam(name = "id") int id
    ) {
        this.ordenVentaBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarOrdenVenta")
    public void guardarOrdenVenta(
        @WebParam(name = "ordenventa") OrdenVenta ordenVenta, 
        @WebParam(name = "estado") Estado estado
    ) {
        this.ordenVentaBO.guardar(ordenVenta, estado);
    }
}
