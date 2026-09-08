package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    List<Cliente> findAll() throws SQLException;
    Cliente findById(Integer id) throws SQLException;
    void insert(Cliente cliente) throws SQLException;
    void update(Cliente cliente) throws SQLException;
    void delete(Integer id) throws SQLException;
}
