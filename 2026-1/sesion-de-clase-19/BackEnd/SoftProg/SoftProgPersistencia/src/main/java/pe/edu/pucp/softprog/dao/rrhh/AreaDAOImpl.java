package pe.edu.pucp.softprog.dao.rrhh;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import pe.edu.pucp.softprog.dao.DefaultBaseDAO;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

public class AreaDAOImpl extends DefaultBaseDAO<Area> implements AreaDAO {
    @Override
    protected PreparedStatement comandoCrear(Connection conn,
                                             Area modelo) throws SQLException {
        String sql = "{call insertarArea(?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn,
                                                  Area modelo) throws SQLException {
        String sql = "{call modificarArea(?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = "{call eliminarArea(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = "{call buscarAreaPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "{call listarAreas()}";
        return conn.prepareCall(sql);
    }

    @Override
    protected Area mapearModelo(ResultSet rs) throws SQLException {
        Area modelo = new Area();
        modelo.setId(rs.getInt(1));
        modelo.setNombre(rs.getString(2));
        modelo.setActivo(rs.getBoolean(3));
        return modelo;
    }
}
