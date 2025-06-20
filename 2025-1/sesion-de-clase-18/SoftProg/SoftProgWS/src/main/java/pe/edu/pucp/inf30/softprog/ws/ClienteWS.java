package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.IClienteBO;
import pe.edu.pucp.inf30.softprog.boimpl.ClienteBOImpl;
import pe.edu.pucp.inf30.softprog.model.clientes.Cliente;

/**
 *
 * @author eric
 */
@WebService(serviceName = "ClienteWS", 
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class ClienteWS {
    private final IClienteBO clienteBO;
    
    public ClienteWS() {
        this.clienteBO = new ClienteBOImpl();
    }
    
    @WebMethod(operationName = "listarClientes")
    public List<Cliente> listarClientes() {
        return this.clienteBO.listar();
    }
    
    @WebMethod(operationName = "obtenerCliente")
    public Cliente obtenerCliente(
        @WebParam(name = "id") int id
    ) {
        return this.clienteBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarCliente")
    public void eliminarCliente(
        @WebParam(name = "id") int id
    ) {
        this.clienteBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarCliente")
    public void guardarCliente(
        @WebParam(name = "cliente") Cliente cliente, 
        @WebParam(name = "estado") Estado estado
    ) {
        this.clienteBO.guardar(cliente, estado);
    }
    
    @WebMethod(operationName = "buscarClientePorDni")
    public Cliente buscarClientePorDni(@WebParam(name = "dni") String dni) {
        return this.clienteBO.buscarPorDni(dni);
    }
}
