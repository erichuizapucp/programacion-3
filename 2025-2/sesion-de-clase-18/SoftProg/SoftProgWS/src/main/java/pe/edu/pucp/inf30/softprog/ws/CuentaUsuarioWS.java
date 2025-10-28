package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.rrhh.CuentaUsuarioBO;
import pe.edu.pucp.inf30.softprog.boimpl.rrhh.CuentaUsuarioBOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
@WebService(serviceName = "CuentaUsuarioWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class CuentaUsuarioWS {
    private final CuentaUsuarioBO cuentaUsuarioBO;
    
    public CuentaUsuarioWS() {
        this.cuentaUsuarioBO = new CuentaUsuarioBOImpl();
    }
    
    @WebMethod(operationName = "listarCuentasUsuario")
    public List<CuentaUsuario> listarCuentasUsuario() {
        return this.cuentaUsuarioBO.listar();
    }
    
    @WebMethod(operationName = "obtenerCuentaUsuario")
    public CuentaUsuario obtenerCuentaUsuario(
        @WebParam(name = "id") int id
    ) {
        return this.cuentaUsuarioBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarCuentaUsuario")
    public void eliminarCuentaUsuario(
        @WebParam(name = "id") int id
    ) {
        this.cuentaUsuarioBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarCuentaUsuario")
    public void guardarCuentaUsuario(
        @WebParam(name = "area") CuentaUsuario cuentaUsuario, 
        @WebParam(name = "estado") Estado estado
    ) {
        this.cuentaUsuarioBO.guardar(cuentaUsuario, estado);
    }
    
    @WebMethod(operationName = "login")
    public boolean login(
        @WebParam(name = "userName") String userName, 
        @WebParam(name = "password") String password
    ) {
        return this.cuentaUsuarioBO.login(userName, password);
    }
}
