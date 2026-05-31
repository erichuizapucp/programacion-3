package pe.edu.pucp.softprog.dao.ventas;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.rrhh.EmpleadoDAOImpl;
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
	private final LineaOrdenVentaDAO lineaDao;

	public OrdenVentaDAOImpl() {
		this.lineaDao = new LineaOrdenVentaDAOImpl();
	}

	@Override
	public Integer crear(OrdenVenta modelo) {
		return ejecutarComando(conn -> {
			Integer idOrden = this.ejecutarComandoCrear(conn, modelo);
			if (idOrden == null) {
				return null;
			}
			modelo.setId(idOrden);
			this.lineaDao.crearLineasPorOrden(conn, idOrden, modelo.getLineas());
			return idOrden;
		});
	}

	@Override
	public boolean actualizar(OrdenVenta modelo) {
		return ejecutarComando(conn -> {
			if (!this.ejecutarComandoActualizar(conn, modelo)) {
				return false;
			}

			this.lineaDao.eliminarLineasPorOrden(conn, modelo.getId());

			this.lineaDao.crearLineasPorOrden(conn, modelo.getId(), modelo.getLineas());
			return true;
		});
	}

	@Override
 	public boolean eliminar(Integer id) {
		return ejecutarComando(conn -> {
			this.lineaDao.eliminarLineasPorOrden(conn, id);
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
				modelo.setLineas(this.lineaDao.leerLineasPorOrden(conn, modelo.getId()));
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
					modelo.setLineas(this.lineaDao.leerLineasPorOrden(conn, modelo.getId()));
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
}
