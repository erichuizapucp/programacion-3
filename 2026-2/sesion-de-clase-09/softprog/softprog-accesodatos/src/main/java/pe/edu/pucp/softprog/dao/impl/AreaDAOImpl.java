package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.AreaDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AreaDAOImpl extends RegistroDAOImpl<Area> implements AreaDAO {
    @Override
    public List<Area> findAll() throws SQLException {
        String sql = "{call listar_areas()}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql);
            ResultSet rs = cmd.executeQuery()) {

            List<Area> areas = new ArrayList<>();
            while (rs.next()) {
                areas.add(mapear(rs, new Area()));
            }
            return areas;
        }
    }

    @Override
    public Area findById(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql = "{call buscar_area_por_id(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Area()) : null;
            }
        }
    }

    @Override
    public Area findByName(String nombre) throws SQLException {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }

        String sql = "{call buscar_area_por_nombre(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_nombre", nombre);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Area()) : null;
            }
        }
    }

    @Override
    public void insert(Area area) throws SQLException {
        if (area == null) {
            throw new IllegalArgumentException("El área no puede ser nula");
        }

        String sql = "{call insertar_area(?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_nombre", area.getNombre());
            cmd.setBoolean("p_activo", area.isActivo());
            cmd.registerOutParameter("p_id", Types.INTEGER);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el área");
            }

            area.setId(cmd.getInt("p_id"));
        }
    }

    @Override
    public void update(Area area) throws SQLException {
        if (area == null) {
            throw new IllegalArgumentException("El área no puede ser nula");
        }

        String sql = "{call modificar_area(?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_nombre", area.getNombre());
            cmd.setBoolean("p_activo", area.isActivo());
            cmd.setInt("p_id", area.getId());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar el área");
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql = "{call eliminar_area(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar el área");
            }
        }
    }

    @Override
    protected Area mapear(ResultSet rs, Area area) throws SQLException {
        super.mapear(rs, area);
        area.setNombre(rs.getString("nombre"));
        return area;
    }
}
