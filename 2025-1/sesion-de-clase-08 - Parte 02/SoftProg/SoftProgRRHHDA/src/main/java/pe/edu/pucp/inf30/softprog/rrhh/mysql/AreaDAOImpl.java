package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IAreaDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;

public class AreaDAOImpl extends BaseDAOImpl<Area> implements IAreaDAO {
    @Override
    protected PreparedStatement comandoInsertar(Connection conn, Area area) throws SQLException {
        String sql = "INSERT AREA(nombre, activo) VALUES(?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        cmd.setString(1, area.getNombre());
        cmd.setBoolean(2, area.isActivo());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoModificar(Connection conn, Area area) throws SQLException {
        String sql = "UPDATE AREA SET nombre = ?, activo = ? WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, area.getNombre());
        cmd.setBoolean(2, area.isActivo());
        cmd.setInt(3, area.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM AREA WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM AREA WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoListar(Connection conn) throws SQLException {
        String sql = "SELECT * FROM AREA";
        PreparedStatement cmd = conn.prepareStatement(sql);
        return cmd;
    }
    
    @Override
    protected Area mapearModelo(ResultSet rs) throws SQLException {
        Area area = new Area();
        area.setId(rs.getInt("id"));
        area.setNombre(rs.getString("nombre"));
        area.setActivo(rs.getBoolean("activo"));
        return area;
    }
}