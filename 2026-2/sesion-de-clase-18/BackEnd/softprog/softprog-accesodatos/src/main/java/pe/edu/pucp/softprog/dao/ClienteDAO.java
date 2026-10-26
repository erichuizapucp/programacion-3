package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO extends DAO<Cliente, Integer> {
    Cliente findByDni(String dni) throws SQLException;
    List<Cliente> filterByName(String nombre) throws SQLException;
    Cliente findByCuenta(String userName) throws SQLException;
    void updateLineaCredito(int id, double lineaCredito) throws SQLException;
}
