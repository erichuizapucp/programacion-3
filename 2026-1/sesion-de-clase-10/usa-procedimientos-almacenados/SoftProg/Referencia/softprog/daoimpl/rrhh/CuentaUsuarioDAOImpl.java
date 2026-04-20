package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.TransaccionalBaseDAO;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
public class CuentaUsuarioDAOImpl extends TransaccionalBaseDAO<CuentaUsuario> 
        implements CuentaUsuarioDAO {
    
    @Override
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

    @Override
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

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = "{call eliminarCuentaUsuario(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = "{call buscarCuentaUsuarioPorId(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException {
        
        String sql = "{call listarCuentaUsuarios()}";
                
        CallableStatement cmd = conn.prepareCall(sql);
        
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

    protected PreparedStatement comandoLogin(Connection conn, String userName, 
            String password) throws SQLException {
        String sql = "{call loginUsuario(?, ?, ?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_username", userName);
        cmd.setString("p_password", password);
        cmd.registerOutParameter("p_valido", Types.BOOLEAN);
        
        return cmd;
    }
    
    @Override
    public boolean login(String username, String password) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoLogin(conn, username, password)) {
                if (cmd instanceof CallableStatement callableCmd) {
                    callableCmd.execute();
                    boolean valido = callableCmd.getBoolean("p_valido");
                    
                    if (!valido) {
                        System.err.println("No se encontro el registro con "
                            + "username: " + username + ", password");
                    }
                    return valido;
                }
                return false;
            }
        });
    }
}
