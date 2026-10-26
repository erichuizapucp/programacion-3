package pe.edu.pucp.softprog.bl;

import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.util.List;

public interface ClienteBL extends RegistroBL<Cliente, Integer> {
    List<Cliente> filterByName(String nombre) throws BLException;
    Cliente findByCuenta(String userName) throws BLException;
    void registrar(Cliente cliente) throws BLException;
}
