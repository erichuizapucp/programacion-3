package pe.edu.pucp.softprog.dao.impl.ventas;

import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;

import java.sql.SQLException;
import java.util.List;

interface LineaOrdenVentaDAO {
    void insertLineas(int idOrden, List<LineaOrdenVenta> lineasOrdenVenta) throws SQLException;
    void deleteLineas(int idOrden) throws SQLException;
    List<LineaOrdenVenta> findByOrderId(int idOrden) throws SQLException;
}
