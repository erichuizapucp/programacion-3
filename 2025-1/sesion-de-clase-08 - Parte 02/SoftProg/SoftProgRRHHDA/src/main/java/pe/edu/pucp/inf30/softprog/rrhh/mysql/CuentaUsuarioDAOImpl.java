package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.inf30.softprog.rrhh.dao.ICuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.CuentaUsuario;

public class CuentaUsuarioDAOImpl extends BaseDAOImpl<CuentaUsuario> implements ICuentaUsuarioDAO {
@Override
    protected PreparedStatement comandoInsertar(Connection conn, CuentaUsuario cuentaUsuario) throws SQLException {
        String sql = "INSERT CUENTA_USUARIO(userName, password, activo) VALUES(?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, cuentaUsuario.getUserName());
        cmd.setString(2, cuentaUsuario.getPassword());
        cmd.setBoolean(3, cuentaUsuario.isActivo());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoModificar(Connection conn, CuentaUsuario cuentaUsuario) throws SQLException {
        String sql = "UPDATE CUENTA_USUARIO SET userName = ?, password = ?, activo = ? WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, cuentaUsuario.getUserName());
        cmd.setString(2, cuentaUsuario.getPassword());
        cmd.setBoolean(3, cuentaUsuario.isActivo());
        cmd.setInt(4, cuentaUsuario.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM CUENTA_USUARIO WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);    
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM CUENTA_USUARIO WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);    
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoListar(Connection conn) throws SQLException {
        String sql = "SELECT * FROM CUENTA_USUARIO";
        PreparedStatement cmd = conn.prepareStatement(sql);
        return cmd;
    }

    @Override
    protected CuentaUsuario mapearModelo(ResultSet rs) throws SQLException {
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setId(rs.getInt("id"));
        cuentaUsuario.setUserName(rs.getString("userName"));
        cuentaUsuario.setPassword(rs.getString("password"));
        cuentaUsuario.setActivo(rs.getBoolean("activo"));

        return cuentaUsuario;
    }
}