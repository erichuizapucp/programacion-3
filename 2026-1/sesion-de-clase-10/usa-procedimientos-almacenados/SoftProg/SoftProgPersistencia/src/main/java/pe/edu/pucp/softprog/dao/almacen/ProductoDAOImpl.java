package pe.edu.pucp.softprog.dao.almacen;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;

import java.sql.*;

public class ProductoDAOImpl extends DefaultBaseDAO<Producto> implements ProductoDAO {
    protected PreparedStatement comandoCrear(Connection conn,
                                             Producto modelo) throws SQLException {
        String sql = "{call insertarProducto(?, ?, ?, ?, ?)}";

        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_unidadMedida", modelo.getUnidadMedida().name());
        cmd.setDouble("p_precio", modelo.getPrecio());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);

        return cmd;
    }

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

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = "{call eliminarProducto(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);

        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = "{call buscarProductoPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "{call listarProductos()}";
        return conn.prepareCall(sql);
    }

    protected Producto mapearModelo(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setId(rs.getInt("id"));
        producto.setNombre(rs.getString("nombre"));
        producto.setUnidadMedida(
                UnidadMedida.valueOf(rs.getString("unidadMedida")));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setActivo(rs.getBoolean("activo"));
        return producto;
    }
}
