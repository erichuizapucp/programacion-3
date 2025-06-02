package pe.edu.pucp.inf30.softprog.bo;

import pe.edu.pucp.inf30.softprog.model.clientes.Cliente;

/**
 *
 * @author eric
 */
public interface IClienteBO extends IBaseBO<Cliente> {
    Cliente buscarPorDni(String dni);
}
