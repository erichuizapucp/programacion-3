package pe.edu.pucp.inf30.softprog.boimpl.clientes;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.clientes.ClienteBO;
import pe.edu.pucp.inf30.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.clientes.ClienteDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;

/**
 *
 * @author eric
 */
public class ClienteBOImpl implements ClienteBO {
    private final ClienteDAO clienteDao;
    
    public ClienteBOImpl() {
        this.clienteDao = new ClienteDAOImpl();
    }
    
    @Override
    public Cliente buscarPorDni(String dni) {
        return this.clienteDao.buscarPorDni(dni);
    }

    @Override
    public List<Cliente> listar() {
        return this.clienteDao.leerTodos();
    }

    @Override
    public Cliente obtener(int id) {
        return this.clienteDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        this.clienteDao.eliminar(id);
    }

    @Override
    public void guardar(Cliente modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.clienteDao.crear(modelo);
        }
        else {
            this.clienteDao.actualizar(modelo);
        }
    }
}
