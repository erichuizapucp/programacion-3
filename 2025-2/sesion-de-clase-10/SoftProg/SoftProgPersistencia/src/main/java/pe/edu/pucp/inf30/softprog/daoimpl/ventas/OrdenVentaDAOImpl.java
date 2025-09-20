package pe.edu.pucp.inf30.softprog.daoimpl.ventas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.ventas.OrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.TransaccionalBaseDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.clientes.ClienteDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
public class OrdenVentaDAOImpl extends TransaccionalBaseDAO<OrdenVenta> 
        implements OrdenVentaDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, OrdenVenta modelo) 
            throws SQLException {
        
        String sql = "{call insertarOrdenVenta(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        
        if (modelo.getCliente() != null) {
            cmd.setInt("p_idCliente", modelo.getCliente().getId());
        }
        else {
            cmd.setNull("p_idCliente", Types.INTEGER);
        }
        
        
        if (modelo.getEmpleado()!= null) {
            cmd.setInt("p_idEmpleado", modelo.getEmpleado().getId());
        }
        else {
            cmd.setNull("p_idEmpleado", Types.INTEGER);
        }
        
        cmd.setDouble("p_total", modelo.getTotal());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            OrdenVenta modelo) throws SQLException {
        
        String sql = "{call modificarOrdenVenta(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        
        if (modelo.getCliente() != null) {
            cmd.setInt("p_idCliente", modelo.getCliente().getId());
        }
        else {
            cmd.setNull("p_idCliente", Types.INTEGER);
        }
        
        
        if (modelo.getEmpleado()!= null) {
            cmd.setInt("p_idEmpleado", modelo.getEmpleado().getId());
        }
        else {
            cmd.setNull("p_idEmpleado", Types.INTEGER);
        }
        
        cmd.setDouble("p_total", modelo.getTotal());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = "{call eliminarOrdenVenta(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = "{call buscarOrdenVentaPorId(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException {
        
        String sql = "{call listarOrdenesVenta()}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        
        return cmd;
    }

    @Override
    protected OrdenVenta mapearModelo(ResultSet rs) throws SQLException {
        OrdenVenta ordenVenta = new OrdenVenta();
        ordenVenta.setId(rs.getInt("id"));
        
        int idEmpleado = rs.getInt("idEmpleado");
        if (!rs.wasNull()) {
            ordenVenta.setEmpleado(
                new EmpleadoDAOImpl().leer(idEmpleado));
        }
        
        int idCliente = rs.getInt("idCliente");
        if (!rs.wasNull()) {
            ordenVenta.setCliente(
                new ClienteDAOImpl().leer(idCliente));
        }
        
        ordenVenta.setTotal(rs.getDouble("total"));
        ordenVenta.setActivo(rs.getBoolean("activo"));
        
        return ordenVenta;
    }
}
