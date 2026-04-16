package pe.edu.pucp.softprog.daoimpl.rrhh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import pe.edu.pucp.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.Area;


public class AreaDAOImpl implements AreaDAO {
    @Override
    public Integer crear(Area modelo) {
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
    public boolean actualizar(Area modelo) {
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
    public Area leer(int id) {
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
    public List<Area> leerTodos() {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
            PreparedStatement cmd = this.comandoLeerTodos(conn)) {
            ResultSet rs = cmd.executeQuery();

            List<Area> modelos = new ArrayList<>();
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
            Area modelo) throws SQLException {

        String sql = "INSERT INTO AREA (nombre, activo) VALUES (?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getNombre());
        cmd.setBoolean(2, modelo.isActivo());

        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
            Area modelo) throws SQLException {

        String sql = "UPDATE AREA SET nombre = ?, activo = ? WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getNombre());
        cmd.setBoolean(2, modelo.isActivo());
        cmd.setInt(3, modelo.getId());

        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
            Integer id) throws SQLException {

        String sql = "DELETE FROM AREA WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);

        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
            Integer id) throws SQLException {

        String sql = "SELECT * FROM AREA WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);

        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(
            Connection conn) throws SQLException {

        String sql = "SELECT * FROM AREA";
        PreparedStatement cmd = conn.prepareStatement(sql);

        return cmd;
    }

    protected Area mapearModelo(ResultSet rs) throws SQLException {
        Area modelo = new Area();
        modelo.setId(rs.getInt(1));
        modelo.setNombre(rs.getString(2));
        modelo.setActivo(rs.getBoolean(3));

        return modelo;
    }
}
