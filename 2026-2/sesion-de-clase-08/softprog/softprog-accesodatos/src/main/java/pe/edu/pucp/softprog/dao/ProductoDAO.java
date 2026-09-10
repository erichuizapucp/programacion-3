package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.sql.SQLException;

public interface ProductoDAO extends DAO<Producto, Integer> {
    Producto findByName(String nombre) throws SQLException;
}
