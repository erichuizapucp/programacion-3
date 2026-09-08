package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    List<Producto> findAll() throws SQLException;
    Producto findById(Integer id) throws SQLException;
    void insert(Producto producto) throws SQLException;
    void update(Producto producto) throws SQLException;
    void delete(Integer id) throws SQLException;
}
