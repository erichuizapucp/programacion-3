package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IAreaDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;

public class AreaDAOImpl implements IAreaDAO {
    @Override
    public int insertar(Area area) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int modificar(Area area) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Area obtenerPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Area> listar() {
        List<Area> areas = new ArrayList<>();
        String sql = "SELECT * FROM AREA";
        try (Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cs = conn.prepareCall(sql);
            ResultSet rs = cs.executeQuery()) {
            
            while (rs.next()) {
                Area area = new Area();
                area.setId(rs.getInt("id"));
                area.setNombre(rs.getString("nombre"));
                area.setActivo(rs.getBoolean("activo"));
                areas.add(area);
            }
        }
        catch (Exception e) {
            System.err.println(e);
            throw new RuntimeException("No se pudo listar las areas.");
        }
        
        return areas;
    }
}