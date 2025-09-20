package pe.edu.pucp.inf30.softprog.bo.clientes;

import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.bo.Gestionable;

/**
 *
 * @author eric
 */
public interface ClienteBO extends Gestionable<Cliente> {
    Cliente buscarPorDni(String dni);
}
