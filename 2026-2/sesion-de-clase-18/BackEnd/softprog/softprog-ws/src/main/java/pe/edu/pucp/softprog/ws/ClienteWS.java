package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.bl.impl.ClienteBLImpl;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.ws.dto.ClienteDTO;
import pe.edu.pucp.softprog.ws.mapper.ClienteMapper;

import java.util.List;

@WebService(serviceName = "ClienteWS", targetNamespace = "http://ws.softprog.pucp.edu.pe/")
public class ClienteWS {
    private final ClienteBL clienteBL = new ClienteBLImpl();

    @WebMethod(operationName = "findAllClientes")
    public List<ClienteDTO> findAllClientes() throws BLException {
        return ClienteMapper.toDTOs(clienteBL.findAll());
    }

    @WebMethod(operationName = "findClienteById")
    public ClienteDTO findClienteById(@WebParam(name = "id") int id) throws BLException {
        Cliente cliente = clienteBL.findById(id);
        return cliente != null ? ClienteMapper.toDTO(cliente) : null;
    }

    @WebMethod(operationName = "insertCliente")
    public int insertCliente(@WebParam(name = "cliente") ClienteDTO dto) throws BLException {
        Cliente cliente = ClienteMapper.toModelo(dto);
        clienteBL.insert(cliente);
        return cliente.getId();
    }

    @WebMethod(operationName = "updateCliente")
    public void updateCliente(@WebParam(name = "cliente") ClienteDTO dto) throws BLException {
        clienteBL.update(ClienteMapper.toModelo(dto));
    }

    @WebMethod(operationName = "deleteCliente")
    public void deleteCliente(@WebParam(name = "id") int id) throws BLException {
        clienteBL.delete(id);
    }

    @WebMethod(operationName = "filterClientesByName")
    public List<ClienteDTO> filterClientesByName(@WebParam(name = "nombre") String nombre) throws BLException {
        return ClienteMapper.toDTOs(clienteBL.filterByName(nombre));
    }

    @WebMethod(operationName = "findClienteByCuenta")
    public ClienteDTO findClienteByCuenta(@WebParam(name = "userName") String userName) throws BLException {
        Cliente cliente = clienteBL.findByCuenta(userName);
        return cliente != null ? ClienteMapper.toDTO(cliente) : null;
    }

    @WebMethod(operationName = "registrar")
    public void registrar(@WebParam(name = "cliente") ClienteDTO dto,
                          @WebParam(name = "userName") String userName,
                          @WebParam(name = "password") String password) throws BLException {
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName(userName);
        cuentaUsuario.setPassword(password);
        cuentaUsuario.setActivo(true);

        Cliente cliente = ClienteMapper.toModelo(dto);
        cliente.setCuentaUsuario(cuentaUsuario);
        clienteBL.registrar(cliente);
    }
}
