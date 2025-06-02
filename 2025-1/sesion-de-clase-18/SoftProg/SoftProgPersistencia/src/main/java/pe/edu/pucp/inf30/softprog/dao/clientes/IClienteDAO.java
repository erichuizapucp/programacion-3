package pe.edu.pucp.inf30.softprog.dao.clientes;

import pe.edu.pucp.inf30.softprog.dao.ICrud;
import pe.edu.pucp.inf30.softprog.model.clientes.Cliente;

/**
 *
 * @author eric
 */
public interface IClienteDAO extends ICrud<Cliente> {
    Cliente buscarPorDni(String dni);
}
