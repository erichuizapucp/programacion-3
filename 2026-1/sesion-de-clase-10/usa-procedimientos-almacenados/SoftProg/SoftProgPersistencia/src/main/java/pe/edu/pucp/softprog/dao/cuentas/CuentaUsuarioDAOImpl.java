package pe.edu.pucp.softprog.dao.cuentas;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class CuentaUsuarioDAOImpl extends DefaultBaseDAO<CuentaUsuario> implements CuentaUsuarioDAO {
    @Override
    public boolean login(String username, String password) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoLogin(conn, username, password)) {
                if (cmd instanceof CallableStatement callableCmd) {
                    callableCmd.execute();
                    return callableCmd.getBoolean("p_valido");
                }
                return false;
            }
        });
    }

    protected PreparedStatement comandoCrear(Connection conn,
                                             CuentaUsuario modelo) throws SQLException {
        String sql = "{call insertarCuentaUsuario(?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_userName", modelo.getUserName());
        cmd.setString("p_password", modelo.getPassword());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
                                                  CuentaUsuario modelo) throws SQLException {
        String sql = "{call modificarCuentaUsuario(?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_userName", modelo.getUserName());
        cmd.setString("p_password", modelo.getPassword());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = "{call eliminarCuentaUsuario(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = "{call buscarCuentaUsuarioPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "{call listarCuentaUsuarios()}";
        return conn.prepareCall(sql);
    }

    protected PreparedStatement comandoLogin(Connection conn,
                                             String username,
                                             String password) throws SQLException {
        String sql = "{call loginUsuario(?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_username", username);
        cmd.setString("p_password", password);
        cmd.registerOutParameter("p_valido", Types.BOOLEAN);
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
