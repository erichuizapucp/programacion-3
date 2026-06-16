package pe.edu.pucp.softprog.dao.ventas;

import pe.edu.pucp.softprog.dao.Persistible;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface LineaOrdenVentaDAO extends Persistible<LineaOrdenVenta, Integer> {
	void crearLineasPorOrden(Connection conn,
							 Integer idOrdenVenta,
							 List<LineaOrdenVenta> lineas) throws SQLException;

	List<LineaOrdenVenta> leerLineasPorOrden(Connection conn,
									 Integer idOrdenVenta) throws SQLException;

	void eliminarLineasPorOrden(Connection conn,
								  Integer idOrdenVenta) throws SQLException;
}




