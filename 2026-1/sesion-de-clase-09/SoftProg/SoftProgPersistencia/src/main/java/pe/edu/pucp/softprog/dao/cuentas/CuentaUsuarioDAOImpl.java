package pe.edu.pucp.softprog.dao.cuentas;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CuentaUsuarioDAOImpl extends DefaultBaseDAO<CuentaUsuario> implements CuentaUsuarioDAO {
    @Override
    public boolean login(String username, String password) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoLogin(conn, username, password);
                 ResultSet rs = cmd.executeQuery()) {
                return rs.next();
            }
        });
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
