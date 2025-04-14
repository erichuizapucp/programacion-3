package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IAreaDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;

public class AreaDAOImpl implements IAreaDAO {
    @Override
    public int insertar(Area area) {
        String sql = "INSERT AREA(nombre, activo) VALUES(?, ?)";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, area.getNombre());
            ps.setBoolean(2, area.isActivo());
            
            if (ps.executeUpdate() == 0) {
                throw new SQLException("La insercion fallo, no se creo ninguna fila.");
            }
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                else {
                    throw new SQLException("La insercion fallo, no se obtuviero ID.");
                }
            }
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo insertar el registro.");
        }
    }

    @Override
    public boolean modificar(Area area) {
        String sql = "UPDATE AREA SET nombre = ?, activo = ? WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cs = conn.prepareStatement(sql)) {
            
            cs.setString(1, area.getNombre());
            cs.setBoolean(2, area.isActivo());
            cs.setInt(3, area.getId());
            
            return cs.executeUpdate() > 0;
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM AREA WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }

    @Override
    public Area buscar(int id) {
        String sql = "SELECT * FROM AREA WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.err.println("No se encontro el area con id: " + id);
                return null;
            }
            
            Area area = new Area();
            area.setId(rs.getInt("id"));
            area.setNombre(rs.getString("nombre"));
            area.setActivo(rs.getBoolean("activo"));
            
            return area;
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }

    @Override
    public List<Area> listar() {
        String sql = "SELECT * FROM AREA";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
            List<Area> areas = new ArrayList<>();
            while (rs.next()) {
                Area area = new Area();
                area.setId(rs.getInt("id"));
                area.setNombre(rs.getString("nombre"));
                area.setActivo(rs.getBoolean("activo"));
                areas.add(area);
            }
            
            return areas;
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }
}