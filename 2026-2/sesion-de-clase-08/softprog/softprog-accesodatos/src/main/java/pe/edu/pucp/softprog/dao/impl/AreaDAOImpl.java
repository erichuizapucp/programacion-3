package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.AreaDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDAOImpl extends RegistroDAOImpl<Area> implements AreaDAO {
    @Override
    public List<Area> findAll() throws SQLException {
        String sql =
                """
                SELECT id, nombre, activo FROM area
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql);
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

        String sql =
                """
                SELECT id, nombre, activo FROM area WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

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

        String sql =
                """
                SELECT id, nombre, activo FROM area WHERE nombre = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setString(1, nombre);

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

        String sql =
                """
                INSERT INTO area (nombre, activo) VALUES (?, ?)
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            cmd.setString(1, area.getNombre());
            cmd.setBoolean(2, area.isActivo());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el área");
            }

            try (ResultSet rs = cmd.getGeneratedKeys()) {
                if (rs.next()) {
                    area.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Area area) throws SQLException {
        if (area == null) {
            throw new IllegalArgumentException("El área no puede ser nula");
        }

        String sql =
                """
                UPDATE area SET nombre = ?, activo = ? WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setString(1, area.getNombre());
            cmd.setBoolean(2, area.isActivo());
            cmd.setInt(3, area.getId());

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

        String sql =
                """
                DELETE FROM area WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

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
