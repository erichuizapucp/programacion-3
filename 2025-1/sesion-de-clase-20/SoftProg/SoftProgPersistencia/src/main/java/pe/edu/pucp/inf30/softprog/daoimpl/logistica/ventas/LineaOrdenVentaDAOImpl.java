package pe.edu.pucp.inf30.softprog.daoimpl.logistica.ventas;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.logistica.ventas.ILineaOrdenVentaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.logistica.almacen.ProductoDAOImpl;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.LineaOrdenVenta;

/**
 *
 * @author eric
 */
public class LineaOrdenVentaDAOImpl extends BaseDAOImpl<LineaOrdenVenta> implements ILineaOrdenVentaDAO {
    @Override
    protected CallableStatement comandoInsertar(Connection conn, LineaOrdenVenta modelo) throws SQLException {
        String sql = "{CALL insertarLineaOrdenVenta(?, ?, ?, ?, ?, ?)}";
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
    protected CallableStatement comandoModificar(Connection conn, LineaOrdenVenta modelo) throws SQLException {
        String sql = "{CALL modificarLineaOrdenVenta(?, ?, ?, ?, ?, ?)}";
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
    protected CallableStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "{CALL eliminarLineaOrdenVenta(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "{CALL buscarLineaOrdenVentaPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoListar(Connection conn) throws SQLException {
        String sql = "{CALL listarLineasOrdenVenta()}";
        CallableStatement cmd = conn.prepareCall(sql);
        return cmd;
    }

    @Override
    protected LineaOrdenVenta mapearModelo(ResultSet rs) throws SQLException {
        LineaOrdenVenta lineaOrdenVenta = new LineaOrdenVenta();
        lineaOrdenVenta.setId(rs.getInt("id"));
        lineaOrdenVenta.setOrdenVenta(new OrdenVentaDAOImpl().buscar(rs.getInt("idOrdenVenta")));
        lineaOrdenVenta.setProducto(new ProductoDAOImpl().buscar(rs.getInt("idProducto")));
        lineaOrdenVenta.setCantidad(rs.getInt("cantidad"));
        lineaOrdenVenta.setSubTotal(rs.getDouble("subTotal"));
        lineaOrdenVenta.setActivo(rs.getBoolean("activo"));
        return lineaOrdenVenta;
    }
}
