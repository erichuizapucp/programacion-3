package pe.edu.pucp.softprog.dao.ventas;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.dao.almacen.ProductoDAOImpl;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

class LineaOrdenVentaDAOImpl extends DefaultBaseDAO<LineaOrdenVenta> implements LineaOrdenVentaDAO {
	private PreparedStatement comandoCrearLinea(Connection conn,
									   Integer idOrdenVenta,
									   LineaOrdenVenta linea) throws SQLException {
		String sql = "{call insertarLineaOrdenVenta(?, ?, ?, ?, ?, ?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_idOrdenVenta", idOrdenVenta);
		cmd.setInt("p_idProducto", linea.getProducto().getId());
		cmd.setInt("p_cantidad", linea.getCantidad());
		cmd.setDouble("p_subTotal", linea.getSubTotal());
		cmd.setBoolean("p_activo", linea.isActivo());
		cmd.registerOutParameter("p_id", Types.INTEGER);
		return cmd;
	}

	private PreparedStatement comandoLeerLineasPorOrden(Connection conn,
										   Integer idOrdenVenta) throws SQLException {
		String sql = "{call listarLineasPorOrdenVenta(?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_idOrdenVenta", idOrdenVenta);
		return cmd;
	}

	private PreparedStatement comandoEliminarLinea(Connection conn,
										  Integer idLineaOrdenVenta) throws SQLException {
		String sql = "{call eliminarLineaOrdenVenta(?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_id", idLineaOrdenVenta);
		return cmd;
	}

	private LineaOrdenVenta mapearLinea(ResultSet rs) throws SQLException {
		LineaOrdenVenta linea = new LineaOrdenVenta();
		linea.setId(rs.getInt("id"));
		linea.setProducto(new ProductoDAOImpl().leer(rs.getInt("idProducto")));

		linea.setCantidad(rs.getInt("cantidad"));
		linea.setSubTotal(rs.getDouble("subTotal"));
		linea.setActivo(rs.getBoolean("activo"));
		return linea;
	}

	@Override
	public Integer crear(LineaOrdenVenta modelo) {
		throw new UnsupportedOperationException("Use crearLineasPorOrden(conn, idOrdenVenta, lineas) para crear lineas");
	}

	@Override
	public boolean actualizar(LineaOrdenVenta modelo) {
		return ejecutarComando(conn -> this.ejecutarComandoActualizar(conn, modelo));
	}

	@Override
	public boolean eliminar(Integer id) {
		return ejecutarComando(conn -> this.ejecutarComandoEliminar(conn, id));
	}

	@Override
	public LineaOrdenVenta leer(Integer id) {
		return ejecutarComando(conn -> {
			try (PreparedStatement cmd = this.comandoLeer(conn, id);
				 ResultSet rs = cmd.executeQuery()) {
				if (!rs.next()) {
					return null;
				}
				return this.mapearModelo(rs);
			}
		});
	}

	@Override
	public List<LineaOrdenVenta> leerTodos() {
		return ejecutarComando(conn -> {
			try (PreparedStatement cmd = this.comandoLeerTodos(conn);
				 ResultSet rs = cmd.executeQuery()) {
				List<LineaOrdenVenta> lineas = new ArrayList<>();
				while (rs.next()) {
					lineas.add(this.mapearModelo(rs));
				}
				return lineas;
			}
		});
	}

	@Override
	public void crearLineasPorOrden(Connection conn,
									Integer idOrdenVenta,
									List<LineaOrdenVenta> lineas) throws SQLException {
		if (lineas == null || lineas.isEmpty()) {
			return;
		}

		for (LineaOrdenVenta linea : lineas) {
			try (PreparedStatement cmd = this.comandoCrearLinea(conn, idOrdenVenta, linea)) {
				if (cmd.executeUpdate() == 0) {
					throw new SQLException("No se pudo insertar una linea de orden de venta");
				}
				if (cmd instanceof CallableStatement callableCmd) {
					linea.setId(callableCmd.getInt("p_id"));
				}
			}
		}
	}

	@Override
	public List<LineaOrdenVenta> leerLineasPorOrden(Connection conn,
									   Integer idOrdenVenta) throws SQLException {
		List<LineaOrdenVenta> lineas = new ArrayList<>();
		try (PreparedStatement cmd = this.comandoLeerLineasPorOrden(conn, idOrdenVenta);
			 ResultSet rs = cmd.executeQuery()) {
			while (rs.next()) {
				try (PreparedStatement cmdLeer = this.comandoLeer(conn, rs.getInt("id"));
					 ResultSet rsLinea = cmdLeer.executeQuery()) {
					if (rsLinea.next()) {
						lineas.add(this.mapearModelo(rsLinea));
					}
				}
			}
		}
		return lineas;
	}

	@Override
	public void eliminarLineasPorOrden(Connection conn,
									  Integer idOrdenVenta) throws SQLException {
		try (PreparedStatement cmd = this.comandoLeerLineasPorOrden(conn, idOrdenVenta);
			 ResultSet rs = cmd.executeQuery()) {
			while (rs.next()) {
				this.ejecutarComandoEliminar(conn, rs.getInt("id"));
			}
		}
	}

	@Override
	protected PreparedStatement comandoCrear(Connection conn,
											  LineaOrdenVenta modelo) throws SQLException {
		throw new UnsupportedOperationException("Use crearLineasPorOrden(conn, idOrdenVenta, lineas) para crear lineas");
	}

	@Override
	protected PreparedStatement comandoActualizar(Connection conn,
											   LineaOrdenVenta modelo) throws SQLException {
		String sql = "{call modificarLineaOrdenVenta(?, ?, ?, ?, ?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_idProducto", modelo.getProducto().getId());
		cmd.setInt("p_cantidad", modelo.getCantidad());
		cmd.setDouble("p_subTotal", modelo.getSubTotal());
		cmd.setBoolean("p_activo", modelo.isActivo());
		cmd.setInt("p_id", modelo.getId());
		return cmd;
	}

	@Override
	protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
		return this.comandoEliminarLinea(conn, id);
	}

	@Override
	protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
		String sql = "{call buscarLineaOrdenVentaPorId(?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_id", id);
		return cmd;
	}

	@Override
	protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
		String sql = "{call listarLineasOrdenVenta()}";
		return conn.prepareCall(sql);
	}

	@Override
	protected LineaOrdenVenta mapearModelo(ResultSet rs) throws SQLException {
		return this.mapearLinea(rs);
	}
}






