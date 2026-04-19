package pe.edu.pucp.softprog.dao.almacen;

import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl implements ProductoDAO {

    @Override
    public Integer crear(Producto modelo) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoCrear(conn, modelo)){
            if (cmd.executeUpdate() == 0) {
                return null;
            }
            try (ResultSet rs = cmd.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : null;
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
    public boolean actualizar(Producto modelo) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoActualizar(conn, modelo)) {
            return cmd.executeUpdate() > 0;
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
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoEliminar(conn, id)) {
            return cmd.executeUpdate() > 0;
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
    public Producto leer(int id) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLeer(conn, id)) {
            ResultSet rs = cmd.executeQuery();

            if (!rs.next()) {
                System.err.println("No se encontro el registro con "
                        + "id: " + id);
                return null;
            }

            return this.mapearModelo(rs);
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
    public java.util.List<Producto> leerTodos() {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLeerTodos(conn)) {
            ResultSet rs = cmd.executeQuery();

            List<Producto> modelos = new ArrayList<>();
            while (rs.next()) {
                modelos.add(this.mapearModelo(rs));
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
