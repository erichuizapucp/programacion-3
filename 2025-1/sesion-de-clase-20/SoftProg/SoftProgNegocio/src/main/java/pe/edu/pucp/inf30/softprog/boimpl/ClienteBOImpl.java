package pe.edu.pucp.inf30.softprog.boimpl;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.IClienteBO;
import pe.edu.pucp.inf30.softprog.dao.clientes.IClienteDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.clientes.ClienteDAOImpl;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.clientes.Cliente;

/**
 *
 * @author eric
 */
public class ClienteBOImpl implements IClienteBO {
    private final IClienteDAO clienteDao;
    
    public ClienteBOImpl() {
        this.clienteDao = new ClienteDAOImpl();
    }
    
    @Override
    public Cliente buscarPorDni(String dni) {
        return this.clienteDao.buscarPorDni(dni);
    }

    @Override
    public List<Cliente> listar() {
        return this.clienteDao.listar();
    }

    @Override
    public Cliente obtener(int id) {
        return this.clienteDao.buscar(id);
    }

    @Override
    public void eliminar(int id) {
        this.clienteDao.eliminar(id);
    }

    @Override
    public void guardar(Cliente modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.clienteDao.insertar(modelo);
        }
        else {
            this.clienteDao.modificar(modelo);
        }
    }
}
