package pe.edu.pucp.softprog.dao.ventas;

import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.dao.almacen.ProductoDAOImpl;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OrdenVentaDAOImpl implements OrdenVentaDAO {
	@Override
	public Integer crear(OrdenVenta modelo) {
		DBManager dbManager = DBFactoryProvider.getManager();
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement cmd = this.comandoCrear(conn, modelo)) {
			if (cmd.executeUpdate() == 0) {
				return null;
			}

			Integer idOrden = this.obtenerIdGenerado(cmd);
			if (idOrden == null) {
				return null;
			}

			modelo.setId(idOrden);
			this.crearLineas(conn, idOrden, modelo.getLineas());
			return idOrden;
		}
		catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
			throw new RuntimeException(e);
		}
		catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean actualizar(OrdenVenta modelo) {
		DBManager dbManager = DBFactoryProvider.getManager();
		try (Connection conn = dbManager.getConnection();
             PreparedStatement cmdActualizar = this.comandoActualizar(conn, modelo)) {

			if (cmdActualizar.executeUpdate() == 0) {
                return false;
            }

			try (PreparedStatement cmdEliminarLineas = this.comandoEliminarLineas(conn, modelo.getId())) {
                cmdEliminarLineas.executeUpdate();
            }

			this.crearLineas(conn, modelo.getId(), modelo.getLineas());
			return true;
		}
		catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
			throw new RuntimeException(e);
		}
		catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public boolean eliminar(int id) {
		DBManager dbManager = DBFactoryProvider.getManager();
		try (Connection conn = dbManager.getConnection()) {
			try (PreparedStatement cmdEliminarLineas = this.comandoEliminarLineas(conn, id)) {
				cmdEliminarLineas.executeUpdate();
			}

			try (PreparedStatement cmdEliminar = this.comandoEliminar(conn, id)) {
				return cmdEliminar.executeUpdate() > 0;
			}
		}
		catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
			throw new RuntimeException(e);
		}
		catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public OrdenVenta leer(int id) {
		DBManager dbManager = DBFactoryProvider.getManager();
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement cmd = this.comandoLeer(conn, id)) {
			ResultSet rs = cmd.executeQuery();
			if (!rs.next()) {
				System.err.println("No se encontro el registro con id: " + id);
				return null;
			}

			OrdenVenta modelo = this.mapearOrdenVenta(rs);
			modelo.setLineas(this.leerLineas(conn, modelo.getId()));
			return modelo;
		}
		catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
			throw new RuntimeException(e);
		}
		catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<OrdenVenta> leerTodos() {
		DBManager dbManager = DBFactoryProvider.getManager();
		try (Connection conn = dbManager.getConnection();
			 PreparedStatement cmd = this.comandoLeerTodos(conn)) {
			ResultSet rs = cmd.executeQuery();
			List<OrdenVenta> modelos = new ArrayList<>();
			while (rs.next()) {
				OrdenVenta modelo = this.mapearOrdenVenta(rs);
				modelo.setLineas(this.leerLineas(conn, modelo.getId()));
				modelos.add(modelo);
			}
			return modelos;
		}
		catch (SQLException e) {
			System.err.println("Error SQL: " + e.getMessage());
			throw new RuntimeException(e);
		}
		catch (Exception e) {
			System.err.println("Error inesperado: " + e.getMessage());
			throw new RuntimeException(e);
		}
	}

	protected PreparedStatement comandoCrear(Connection conn,
											 OrdenVenta modelo) throws SQLException {
		String sql = """
			INSERT INTO ORDENVENTA (idCliente, idEmpleado, total, activo)
			VALUES (?, ?, ?, ?)
			""";
		PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		cmd.setInt(1, modelo.getCliente().getId());
		if (modelo.getEmpleado() == null) {
			cmd.setNull(2, java.sql.Types.INTEGER);
		}
		else {
			cmd.setInt(2, modelo.getEmpleado().getId());
		}
		cmd.setDouble(3, modelo.getTotal());
		cmd.setBoolean(4, modelo.isActivo());
		return cmd;
	}

	protected PreparedStatement comandoActualizar(Connection conn,
												  OrdenVenta modelo) throws SQLException {
		String sql = """
			UPDATE ORDENVENTA
			SET idCliente = ?,
				idEmpleado = ?,
				total = ?,
				activo = ?
			WHERE id = ?
			""";
		PreparedStatement cmd = conn.prepareStatement(sql);
		cmd.setInt(1, modelo.getCliente().getId());
		if (modelo.getEmpleado() == null) {
			cmd.setNull(2, java.sql.Types.INTEGER);
		}
		else {
			cmd.setInt(2, modelo.getEmpleado().getId());
		}
		cmd.setDouble(3, modelo.getTotal());
		cmd.setBoolean(4, modelo.isActivo());
		cmd.setInt(5, modelo.getId());
		return cmd;
	}

	protected PreparedStatement comandoEliminar(Connection conn,
												Integer id) throws SQLException {
		String sql = """
			DELETE FROM ORDENVENTA WHERE id = ?
			""";
		PreparedStatement cmd = conn.prepareStatement(sql);
		cmd.setInt(1, id);
		return cmd;
	}

	protected PreparedStatement comandoLeer(Connection conn,
											Integer id) throws SQLException {
		String sql = """
			SELECT * FROM ORDENVENTA WHERE id = ?
			""";
		PreparedStatement cmd = conn.prepareStatement(sql);
		cmd.setInt(1, id);
		return cmd;
	}

	protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
		String sql = """
			SELECT * FROM ORDENVENTA
			""";
		return conn.prepareStatement(sql);
	}

	protected PreparedStatement comandoCrearLinea(Connection conn,
                                                  Integer idOrdenVenta,
                                                  LineaOrdenVenta linea) throws SQLException {
		String sql = """
			INSERT INTO LINEAORDENVENTA (idOrdenVenta, idProducto, cantidad, subTotal, activo)
			VALUES (?, ?, ?, ?, ?)
			""";
		PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		cmd.setInt(1, idOrdenVenta);
		cmd.setInt(2, linea.getProducto().getId());
		cmd.setInt(3, linea.getCantidad());
		cmd.setDouble(4, linea.getSubTotal());
		cmd.setBoolean(5, linea.isActivo());
		return cmd;
	}

	protected PreparedStatement comandoLeerLineas(Connection conn,
                                                  Integer idOrdenVenta) throws SQLException {
		String sql = """
			SELECT * FROM LINEAORDENVENTA WHERE idOrdenVenta = ?
			""";
		PreparedStatement cmd = conn.prepareStatement(sql);
		cmd.setInt(1, idOrdenVenta);
		return cmd;
	}

	protected PreparedStatement comandoEliminarLineas(Connection conn,
											  Integer idOrdenVenta) throws SQLException {
		String sql = """
			DELETE FROM LINEAORDENVENTA WHERE idOrdenVenta = ?
			""";
		PreparedStatement cmd = conn.prepareStatement(sql);
		cmd.setInt(1, idOrdenVenta);
		return cmd;
	}

	protected Integer obtenerIdGenerado(PreparedStatement cmd) throws SQLException {
		ResultSet rs = cmd.getGeneratedKeys();
		return rs.next() ? rs.getInt(1) : null;
	}

	protected OrdenVenta mapearOrdenVenta(ResultSet rs) throws SQLException {
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
}
