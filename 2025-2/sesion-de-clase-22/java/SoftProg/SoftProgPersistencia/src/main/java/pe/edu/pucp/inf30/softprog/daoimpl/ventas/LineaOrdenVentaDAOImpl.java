package pe.edu.pucp.inf30.softprog.daoimpl.ventas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.dao.ventas.LineaOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.TransaccionalBaseDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.almacen.ProductoDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.ventas.LineaOrdenVenta;

/**
 *
 * @author eric
 */
public class LineaOrdenVentaDAOImpl extends TransaccionalBaseDAO<LineaOrdenVenta> 
        implements LineaOrdenVentaDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, 
            LineaOrdenVenta modelo) throws SQLException {
        
        String sql = "{call insertarLineaOrdenVenta(?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idOrdenVenta", modelo.getOrdenVenta().getId());
        cmd.setInt("p_idProducto", modelo.getProducto().getId());
        cmd.setInt("p_cantidad", modelo.getCantidad());
        cmd.setDouble("p_subTotal", modelo.getSubTotal());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            LineaOrdenVenta modelo) throws SQLException {
        
        String sql = "{call modificarLineaOrdenVenta(?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idOrdenVenta", modelo.getOrdenVenta().getId());
        cmd.setInt("p_idProducto", modelo.getProducto().getId());
        cmd.setInt("p_cantidad", modelo.getCantidad());
        cmd.setDouble("p_subTotal", modelo.getSubTotal());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, 
            Integer id) throws SQLException {
         
        String sql = "{call eliminarLineaOrdenVenta(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = "{call buscarLineaOrdenVentaPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException {
        
        String sql = "{call listarLineasOrdenVenta()}";
        CallableStatement cmd = conn.prepareCall(sql);
        return cmd;
    }

    @Override
    protected LineaOrdenVenta mapearModelo(ResultSet rs) throws SQLException {
        LineaOrdenVenta lineaOrdenVenta = new LineaOrdenVenta();
        lineaOrdenVenta.setId(rs.getInt("id"));
        lineaOrdenVenta.setOrdenVenta(
                new OrdenVentaDAOImpl().leer(rs.getInt("idOrdenVenta")));
        lineaOrdenVenta.setProducto(
                new ProductoDAOImpl().leer(rs.getInt("idProducto")));
        lineaOrdenVenta.setCantidad(rs.getInt("cantidad"));
        lineaOrdenVenta.setSubTotal(rs.getDouble("subTotal"));
        lineaOrdenVenta.setActivo(rs.getBoolean("activo"));
        return lineaOrdenVenta;
    }

    protected PreparedStatement comandoLeerTodosPorOrden(Connection conn, 
            int idOrden) throws SQLException {
        
        String sql = "{call listarLineasPorOrdenVenta(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idOrdenVenta", idOrden);
        return cmd;
    }
    
    public List<LineaOrdenVenta> leerTodosPorOrden(int idOrden) {
        return ejecutarComando(conn -> leerTodosPorOrden(idOrden, conn));
    }
    
    @Override
    public List<LineaOrdenVenta> leerTodosPorOrden(int idOrden, Connection conn) {
        try (PreparedStatement cmd = this.comandoLeerTodosPorOrden(conn, idOrden)) {
            ResultSet rs = cmd.executeQuery();

            List<LineaOrdenVenta> modelos = new ArrayList<>();
            while (rs.next()) {
                modelos.add(this.mapearModelo(rs));
            }

            return modelos;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
