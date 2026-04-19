package pe.edu.pucp.softprog.dao.cuentas;

import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CuentaUsuarioDAOImpl implements CuentaUsuarioDAO {
    @Override
    public Integer crear(CuentaUsuario modelo) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoCrear(conn, modelo)) {
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
    public boolean actualizar(CuentaUsuario modelo) {
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
    public CuentaUsuario leer(int id) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLeer(conn, id)) {
            ResultSet rs = cmd.executeQuery();
            if (!rs.next()) {
                System.err.println("No se encontro el registro con id: " + id);
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
    public List<CuentaUsuario> leerTodos() {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLeerTodos(conn)) {
            ResultSet rs = cmd.executeQuery();
            List<CuentaUsuario> modelos = new ArrayList<>();
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

    @Override
    public boolean login(String username, String password) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLogin(conn, username, password)) {
            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next();
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

    protected PreparedStatement comandoCrear(Connection conn,
                                             CuentaUsuario modelo) throws SQLException {
        String sql = """
            INSERT INTO CUENTA_USUARIO (userName, password, activo)
            VALUES (?, ?, ?)
            """;
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getUserName());
        cmd.setString(2, modelo.getPassword());
        cmd.setBoolean(3, modelo.isActivo());
        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
                                                  CuentaUsuario modelo) throws SQLException {
        String sql = """
            UPDATE CUENTA_USUARIO
            SET userName = ?,
                password = ?,
                activo = ?
            WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getUserName());
        cmd.setString(2, modelo.getPassword());
        cmd.setBoolean(3, modelo.isActivo());
        cmd.setInt(4, modelo.getId());
        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = """
            DELETE FROM CUENTA_USUARIO WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = """
            SELECT * FROM CUENTA_USUARIO WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = """
            SELECT * FROM CUENTA_USUARIO
            """;
        return conn.prepareStatement(sql);
    }

    protected PreparedStatement comandoLogin(Connection conn,
                                             String username,
                                             String password) throws SQLException {
        String sql = """
            SELECT id
            FROM CUENTA_USUARIO
            WHERE userName = ?
              AND password = ?
              AND activo = TRUE
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, username);
        cmd.setString(2, password);
        return cmd;
    }

    protected CuentaUsuario mapearModelo(ResultSet rs) throws SQLException {
        CuentaUsuario modelo = new CuentaUsuario();
        modelo.setId(rs.getInt("id"));
        modelo.setUserName(rs.getString("userName"));
        modelo.setPassword(rs.getString("password"));
        modelo.setActivo(rs.getBoolean("activo"));
        return modelo;
    }
}
