package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO extends DAO<Producto, Integer> {
    Producto findByName(String nombre) throws SQLException;
    List<Producto> filterByName(String nombre) throws SQLException;
}
