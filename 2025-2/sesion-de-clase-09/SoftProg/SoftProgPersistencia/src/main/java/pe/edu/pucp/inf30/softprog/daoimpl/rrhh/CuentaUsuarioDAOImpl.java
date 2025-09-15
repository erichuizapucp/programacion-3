package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAO;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
public class CuentaUsuarioDAOImpl extends BaseDAO<CuentaUsuario> 
        implements CuentaUsuarioDAO {
    
    @Override
    protected PreparedStatement comandoCrear(Connection conn, 
            CuentaUsuario modelo) throws SQLException {
        
        String sql = 
                "INSERT INTO CUENTA_USUARIO ("
                + "userName, "
                + " password, "
                + " activo) "
                + "VALUES (?, ?, ?)";
        
        PreparedStatement cmd = conn.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getUserName());
        cmd.setString(2, modelo.getPassword());
        cmd.setBoolean(3, modelo.isActivo());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            CuentaUsuario modelo) throws SQLException {
        
        String sql = 
                "UPDATE CUENTA_USUARIO "
                + "SET "
                + " userName = ?, "
                + " password = ?, "
                + " activo = ? "
                + "WHERE "
                + " id = ?";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getUserName());
        cmd.setString(2, modelo.getPassword());
        cmd.setBoolean(3, modelo.isActivo());
        cmd.setInt(4, modelo.getId());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = 
                "DELETE "
                + "FROM CUENTA_USUARIO "
                + "WHERE id = ?";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = 
                "SELECT "
                + " id, "
                + " userName, "
                + " password, "
                + " activo "
                + "FROM CUENTA_USUARIO "
                + "WHERE "
                + " id = ?";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException {
        
        String sql = 
                "SELECT "
                + " id, "
                + " userName, "
                + " password, "
                + " activo "
                + "FROM CUENTA_USUARIO";
                
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
