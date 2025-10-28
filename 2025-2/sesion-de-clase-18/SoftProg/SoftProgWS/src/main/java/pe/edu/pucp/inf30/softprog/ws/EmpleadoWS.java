package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.bo.rrhh.EmpleadoBO;
import pe.edu.pucp.inf30.softprog.boimpl.rrhh.EmpleadoBOImpl;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;

/**
 *
 * @author eric
 */
@WebService(serviceName = "EmpleadoWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class EmpleadoWS {
    private final EmpleadoBO empleadoBO;
    
    public EmpleadoWS() {
        this.empleadoBO = new EmpleadoBOImpl();
    }
    
    @WebMethod(operationName = "listarEmpleados")
    public List<Empleado> listarEmpleados() {
        return this.empleadoBO.listar();
    }
    
    @WebMethod(operationName = "obtenerEmpleaedo")
    public Empleado obtenerEmpleado(
        @WebParam(name = "id") int id
    ) {
        return this.empleadoBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarEmpleado")
    public void eliminarEmpleado(
        @WebParam(name = "id") int id
    ) {
        this.empleadoBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarEmpleado")
    public void guardarEmpleado(
        @WebParam(name = "empleado") Empleado empleado, 
        @WebParam(name = "estado") Estado estado
    ) {
        this.empleadoBO.guardar(empleado, estado);
    }
    
    @WebMethod(operationName = "buscarEmpleadoPorDni")
    public Empleado buscarEmpleadpPorDni(@WebParam(name = "dni") String dni) {
        return this.empleadoBO.buscarPorDni(dni);
    }
}
