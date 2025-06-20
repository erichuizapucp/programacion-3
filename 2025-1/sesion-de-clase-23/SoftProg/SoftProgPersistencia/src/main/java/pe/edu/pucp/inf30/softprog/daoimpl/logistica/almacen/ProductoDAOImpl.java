package pe.edu.pucp.inf30.softprog.daoimpl.logistica.almacen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.logistica.almacen.IProductoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAOImpl;
import pe.edu.pucp.inf30.softprog.model.logistica.almacen.Producto;

/**
 *
 * @author eric
 */
public class ProductoDAOImpl extends BaseDAOImpl<Producto> implements IProductoDAO {
    @Override
    protected CallableStatement comandoInsertar(Connection conn, Producto modelo) throws SQLException {
        String sql = "{CALL insertarProducto(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_unidadMedida", modelo.getUnidadMedida());
        cmd.setDouble("p_precio", modelo.getPrecio());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    @Override
    protected CallableStatement comandoModificar(Connection conn, Producto modelo) throws SQLException {
        String sql = "{CALL modificarProducto(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_unidadMedida", modelo.getUnidadMedida());
        cmd.setDouble("p_precio", modelo.getPrecio());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    @Override
    protected CallableStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "{CALL eliminarProducto(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "{CALL buscarProductoPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoListar(Connection conn) throws SQLException {
        String sql = "{CALL listarProductos()}";
        CallableStatement cmd = conn.prepareCall(sql);
        return cmd;
    }

    @Override
    protected Producto mapearModelo(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setUnidadMedida(rs.getString("unidadMedida"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setActivo(rs.getBoolean("activo"));
        return producto;
    }
}
