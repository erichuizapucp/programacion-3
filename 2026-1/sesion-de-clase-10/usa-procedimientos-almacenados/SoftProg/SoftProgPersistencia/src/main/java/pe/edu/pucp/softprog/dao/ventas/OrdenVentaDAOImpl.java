package pe.edu.pucp.softprog.dao.ventas;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.dao.almacen.ProductoDAOImpl;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OrdenVentaDAOImpl extends DefaultBaseDAO<OrdenVenta> implements OrdenVentaDAO {
	@Override
	public Integer crear(OrdenVenta modelo) {
		return ejecutarComando(conn -> {
			Integer idOrden = this.ejecutarComandoCrear(conn, modelo);
			if (idOrden == null) {
				return null;
			}
			modelo.setId(idOrden);
			this.crearLineas(conn, idOrden, modelo.getLineas());
			return idOrden;
		});
	}

	@Override
	public boolean actualizar(OrdenVenta modelo) {
		return ejecutarComando(conn -> {
			if (!this.ejecutarComandoActualizar(conn, modelo)) {
				return false;
			}

			this.eliminarLineasPorOrden(conn, modelo.getId());

			this.crearLineas(conn, modelo.getId(), modelo.getLineas());
			return true;
		});
	}

	@Override
 	public boolean eliminar(Integer id) {
		return ejecutarComando(conn -> {
			this.eliminarLineasPorOrden(conn, id);
			return this.ejecutarComandoEliminar(conn, id);
		});
	}

	@Override
	public OrdenVenta leer(Integer id) {
		return ejecutarComando(conn -> {
			try (PreparedStatement cmd = this.comandoLeer(conn, id);
				 ResultSet rs = cmd.executeQuery()) {
				if (!rs.next()) {
					System.err.println("No se encontro el registro con id: " + id);
					return null;
				}

				OrdenVenta modelo = this.mapearModelo(rs);
				modelo.setLineas(this.leerLineas(conn, modelo.getId()));
				return modelo;
			}
		});
	}

	@Override
	public List<OrdenVenta> leerTodos() {
		return ejecutarComando(conn -> {
			try (PreparedStatement cmd = this.comandoLeerTodos(conn);
				 ResultSet rs = cmd.executeQuery()) {
				List<OrdenVenta> modelos = new ArrayList<>();
				while (rs.next()) {
					OrdenVenta modelo = this.mapearModelo(rs);
					modelo.setLineas(this.leerLineas(conn, modelo.getId()));
					modelos.add(modelo);
				}
				return modelos;
			}
		});
	}

	protected PreparedStatement comandoCrear(Connection conn,
											 OrdenVenta modelo) throws SQLException {
		String sql = "{call insertarOrdenVenta(?, ?, ?, ?, ?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_idCliente", modelo.getCliente().getId());
		if (modelo.getEmpleado() == null) {
			cmd.setNull("p_idEmpleado", Types.INTEGER);
		}
		else {
			cmd.setInt("p_idEmpleado", modelo.getEmpleado().getId());
		}
		cmd.setDouble("p_total", modelo.getTotal());
		cmd.setBoolean("p_activo", modelo.isActivo());
		cmd.registerOutParameter("p_id", Types.INTEGER);
		return cmd;
	}

	protected PreparedStatement comandoActualizar(Connection conn,
												  OrdenVenta modelo) throws SQLException {
		String sql = "{call modificarOrdenVenta(?, ?, ?, ?, ?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_idCliente", modelo.getCliente().getId());
		if (modelo.getEmpleado() == null) {
			cmd.setNull("p_idEmpleado", Types.INTEGER);
		}
		else {
			cmd.setInt("p_idEmpleado", modelo.getEmpleado().getId());
		}
		cmd.setDouble("p_total", modelo.getTotal());
		cmd.setBoolean("p_activo", modelo.isActivo());
		cmd.setInt("p_id", modelo.getId());
		return cmd;
	}

	protected PreparedStatement comandoEliminar(Connection conn,
												Integer id) throws SQLException {
		String sql = "{call eliminarOrdenVenta(?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_id", id);
		return cmd;
	}

	protected PreparedStatement comandoLeer(Connection conn,
											Integer id) throws SQLException {
		String sql = "{call buscarOrdenVentaPorId(?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_id", id);
		return cmd;
	}

	protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
		String sql = "{call listarOrdenesVenta()}";
		return conn.prepareCall(sql);
	}

	protected PreparedStatement comandoCrearLinea(Connection conn,
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

	protected PreparedStatement comandoLeerLineas(Connection conn,
                                                  Integer idOrdenVenta) throws SQLException {
		String sql = "{call listarLineasPorOrdenVenta(?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_idOrdenVenta", idOrdenVenta);
		return cmd;
	}

	protected PreparedStatement comandoEliminarLinea(Connection conn,
													 Integer idLineaOrdenVenta) throws SQLException {
		String sql = "{call eliminarLineaOrdenVenta(?)}";
		CallableStatement cmd = conn.prepareCall(sql);
		cmd.setInt("p_id", idLineaOrdenVenta);
		return cmd;
	}

	@Override
	protected OrdenVenta mapearModelo(ResultSet rs) throws SQLException {
		OrdenVenta modelo = new OrdenVenta();
		modelo.setId(rs.getInt("id"));
		modelo.setCliente(new ClienteDAOImpl().leer(rs.getInt("idCliente")));

		int idEmpleado = rs.getInt("idEmpleado");
		if (!rs.wasNull()) {
			modelo.setEmpleado(new EmpleadoDAOImpl().leer(idEmpleado));
		}

		modelo.setTotal(rs.getDouble("total"));
		modelo.setActivo(rs.getBoolean("activo"));
		return modelo;
	}

	protected LineaOrdenVenta mapearLinea(ResultSet rs) throws SQLException {
		LineaOrdenVenta linea = new LineaOrdenVenta();
		linea.setId(rs.getInt("id"));
		linea.setProducto(new ProductoDAOImpl().leer(rs.getInt("idProducto")));

		linea.setCantidad(rs.getInt("cantidad"));
		linea.setSubTotal(rs.getDouble("subTotal"));
		linea.setActivo(rs.getBoolean("activo"));
		return linea;
	}

	protected void crearLineas(Connection conn,
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
			}
		}
	}

	protected List<LineaOrdenVenta> leerLineas(Connection conn,
                                               Integer idOrdenVenta) throws SQLException {
		try (PreparedStatement cmd = this.comandoLeerLineas(conn, idOrdenVenta);
			 ResultSet rs = cmd.executeQuery()) {
			List<LineaOrdenVenta> lineas = new ArrayList<>();
			while (rs.next()) {
				lineas.add(this.mapearLinea(rs));
			}
			return lineas;
		}
	}

	protected void eliminarLineasPorOrden(Connection conn,
										  Integer idOrdenVenta) throws SQLException {
		try (PreparedStatement cmdLeerLineas = this.comandoLeerLineas(conn, idOrdenVenta);
			 ResultSet rs = cmdLeerLineas.executeQuery()) {
			while (rs.next()) {
				int idLinea = rs.getInt("id");
				try (PreparedStatement cmdEliminarLinea = this.comandoEliminarLinea(conn, idLinea)) {
					cmdEliminarLinea.executeUpdate();
				}
			}
		}
	}
}
