package pe.edu.pucp.inf30.softprog.daoimpl.logistica.ventas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.IOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.clientes.ClienteDAOImpl;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
public class OrdenVentaDAOImpl extends BaseDAOImpl<OrdenVenta> implements IOrdenVentaDAO {
    @Override
    protected CallableStatement comandoInsertar(Connection conn, OrdenVenta modelo) throws SQLException {
        String sql = "{CALL insertarOrdenVenta(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idCliente", modelo.getCliente().getId());
        cmd.setNull("p_idEmpleado", Types.INTEGER);
        cmd.setDouble("p_total", modelo.getTotal());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    @Override
    protected CallableStatement comandoModificar(Connection conn, OrdenVenta modelo) throws SQLException {
        String sql = "{CALL modificarOrdenVenta(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idCliente", modelo.getCliente().getId());
        cmd.setNull("p_idEmpleado", Types.INTEGER);
        cmd.setDouble("p_total", modelo.getTotal());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    @Override
    protected CallableStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "{CALL eliminarOrdenVenta(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "{CALL buscarOrdenVentaPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoListar(Connection conn) throws SQLException {
        String sql = "{CALL listarOrdenesVenta()}";
        CallableStatement cmd = conn.prepareCall(sql);
        return cmd;
    }

    @Override
    protected OrdenVenta mapearModelo(ResultSet rs) throws SQLException {
        OrdenVenta ordenVenta = new OrdenVenta();
        ordenVenta.setId(rs.getInt("id"));
        ordenVenta.setCliente(new ClienteDAOImpl().buscar(rs.getInt("idCliente")));
        ordenVenta.setTotal(rs.getDouble("total"));
        ordenVenta.setActivo(rs.getBoolean("activo"));
        return ordenVenta;
    }
}