package pe.edu.pucp.softprog.dao.almacen;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;

import java.sql.*;

public class ProductoDAOImpl extends DefaultBaseDAO<Producto> implements ProductoDAO {
    protected PreparedStatement comandoCrear(Connection conn,
                                             Producto modelo) throws SQLException {
        String sql = """
            INSERT INTO PRODUCTO (
                nombre,
                unidadMedida,
                precio,
                activo
            ) VALUES (?, ?, ?, ?)
            """;

        PreparedStatement cmd = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getNombre());
        cmd.setString(2,modelo.getUnidadMedida().name());
        cmd.setDouble(3, modelo.getPrecio());
        cmd.setBoolean(4, modelo.isActivo());

        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
                                                  Producto modelo) throws SQLException {
        String sql = """
            UPDATE PRODUCTO
            SET nombre = ?,
                unidadMedida = ?,
                precio = ?,
                activo = ?
            WHERE id = ?
            """;

        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getNombre());
        cmd.setString(2, modelo.getUnidadMedida().name());
        cmd.setDouble(3, modelo.getPrecio());
        cmd.setBoolean(4, modelo.isActivo());
        cmd.setInt(5, modelo.getId());

        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = """
            DELETE FROM PRODUCTO WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);

        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = """
            SELECT * FROM PRODUCTO WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = """
            SELECT * FROM PRODUCTO
            """;
        return conn.prepareStatement(sql);
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
