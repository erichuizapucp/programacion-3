package pe.edu.pucp.inf30.softprog.daoimpl.almacen;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.almacen.ProductoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAO;
import pe.edu.pucp.inf30.softprog.modelo.almacen.Producto;
import pe.edu.pucp.inf30.softprog.modelo.almacen.UnidadMedida;

/**
 *
 * @author eric
 */
public class ProductoDAOImpl extends BaseDAO<Producto> 
        implements ProductoDAO {
    
    @Override
    protected PreparedStatement comandoCrear(Connection conn, Producto modelo) 
            throws SQLException {
        
        String sql = "{call insertarProducto(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_unidadMedida", modelo.getUnidadMedida().name());
        cmd.setDouble("p_precio", modelo.getPrecio());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            Producto modelo) throws SQLException {
        
        String sql = "{call modificarProducto(?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_unidadMedida", modelo.getUnidadMedida().name());
        cmd.setDouble("p_precio", modelo.getPrecio());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) throws SQLException {
        String sql = "{call eliminarProducto(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) throws SQLException {
        String sql = "{call buscarProductoPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "{call listarProductos()}";
        CallableStatement cmd = conn.prepareCall(sql);
        return cmd;
    }

    @Override
    protected Producto mapearModelo(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setUnidadMedida(UnidadMedida.valueOf(rs.getString("unidadMedida")));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setActivo(rs.getBoolean("activo"));
        return producto;
    }
}
