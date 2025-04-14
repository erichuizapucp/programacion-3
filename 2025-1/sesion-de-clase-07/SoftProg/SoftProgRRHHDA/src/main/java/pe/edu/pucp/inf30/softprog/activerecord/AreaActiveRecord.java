package pe.edu.pucp.inf30.softprog.activerecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.config.DBManager;

/**
 *
 * @author eric
 * Esta es una clase creada para demostrar el patron de interacción 
 * con bases de datos Active Record
 * cabe aclarar que este no es el patron de interacción con la base de datos
 * que vamos a usar en el curso.
 */
public class AreaActiveRecord {
    private int id;
    private String nombre;
    private boolean activo;

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    public int insertar() {
        String sql = "INSERT AREA(nombre, activo) VALUES(?, ?)";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, 
                    Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, this.nombre);
            ps.setBoolean(2, this.activo);
            
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
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }
    
    public boolean actualizar() {
        String sql = "UPDATE AREA SET nombre = ?, activo = ? WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cs = conn.prepareStatement(sql)) {
            
            cs.setString(1, this.nombre);
            cs.setBoolean(2, this.activo);
            cs.setInt(3, this.id);
            
            return cs.executeUpdate() > 0;
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }
    
    public boolean eliminar() {
        String sql = "DELETE FROM AREA WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, this.id);
            
            return ps.executeUpdate() > 0;
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }
    
    public static AreaActiveRecord buscar(int id) {
        String sql = "SELECT * FROM AREA WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) {
                throw new Exception("No se encontro el area con id: " + id);
            }
            
            AreaActiveRecord area = new AreaActiveRecord();
            area.id = rs.getInt("id");
            area.setNombre(rs.getString("nombre"));
            area.setActivo(rs.getBoolean("activo"));
            
            return area;
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
    }
    
    public static List<AreaActiveRecord> listar() {
        String sql = "SELECT * FROM AREA";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
            List<AreaActiveRecord> areas = new ArrayList<>();
            while (rs.next()) {
                AreaActiveRecord area = new AreaActiveRecord();
                area.id = rs.getInt("id");
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