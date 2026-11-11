package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.SQLException;
import java.util.List;

public interface OrdenVentaDAO extends DAO<OrdenVenta, Integer> {
    List<OrdenVenta> findByCuenta(String userName) throws SQLException;
}
