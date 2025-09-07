package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.inf30.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;

/**
 *
 * @author eric
 */
public class AreaDAOImpl extends BaseDAO<Area> implements AreaDAO {    
    @Override
    protected PreparedStatement comandoCrear(Connection conn, 
            Area modelo) throws SQLException {
        
        String sql = "INSERT INTO AREA (nombre, activo) VALUES (?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, modelo.getNombre());
        cmd.setBoolean(2, modelo.isActivo());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            Area modelo) throws SQLException {
        
        String sql = "UPDATE AREA SET nombre = ?, activo = ? WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, modelo.getNombre());
        cmd.setBoolean(2, modelo.isActivo());
        cmd.setInt(3, modelo.getId());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = "DELETE FROM AREA WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, 
            Integer id) throws SQLException {
        
        String sql = "SELECT * FROM AREA WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(
            Connection conn) throws SQLException {
        
        String sql = "SELECT * FROM AREA";
        PreparedStatement cmd = conn.prepareStatement(sql);
        
        return cmd;
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
