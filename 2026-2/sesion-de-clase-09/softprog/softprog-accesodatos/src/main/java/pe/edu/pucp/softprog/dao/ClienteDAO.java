package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.SQLException;

public interface ClienteDAO extends DAO<Cliente, Integer> {
    Cliente findByDni(String dni) throws SQLException;
}
