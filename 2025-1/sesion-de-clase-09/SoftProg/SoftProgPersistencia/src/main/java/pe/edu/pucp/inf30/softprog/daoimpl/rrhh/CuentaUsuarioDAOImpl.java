package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.rrhh.ICuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAOImpl;
import pe.edu.pucp.inf30.softprog.rrhh.model.CuentaUsuario;

public class CuentaUsuarioDAOImpl extends BaseDAOImpl<CuentaUsuario> implements ICuentaUsuarioDAO {    
    @Override
    protected CallableStatement comandoInsertar(Connection conn, CuentaUsuario cuentaUsuario) throws SQLException {
        String sql = "{CALL insertarCuentaUsuario(?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_userName", cuentaUsuario.getUserName());
        cmd.setString("p_password", cuentaUsuario.getPassword());
        cmd.setBoolean("p_activo", cuentaUsuario.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }
    
    @Override
    protected CallableStatement comandoModificar(Connection conn, CuentaUsuario cuentaUsuario) throws SQLException {
        String sql = "{CALL modificarCuentaUsuario(?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_userName", cuentaUsuario.getUserName());
        cmd.setString("p_password", cuentaUsuario.getPassword());
        cmd.setBoolean("p_activo", cuentaUsuario.isActivo());
        cmd.setInt("p_id", cuentaUsuario.getId());
        return cmd;
    }

    @Override
    protected CallableStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "{CALL eliminarCuentaUsuario(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "{CALL buscarCuentaUsuarioPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoListar(Connection conn) throws SQLException {
        String sql = "{CALL listarCuentaUsuarios()}";
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
}