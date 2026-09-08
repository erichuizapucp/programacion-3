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

public class AreaDAOImpl implements AreaDAO {
    @Override
    public List<Area> findAll() throws SQLException {
        String sql =
                """
                SELECT id, nombre, activo
                FROM area
                """;

        List<Area> areas = new ArrayList<>();
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery()) {

            while(rs.next()) {
                Area area = new Area();
                area.setId(rs.getInt("id"));
                area.setNombre(rs.getString("nombre"));
                area.setActivo(rs.getBoolean("activo"));
                areas.add(area);
            }
        }
        return areas;
    }

    @Override
    public Area findById(int id) throws SQLException {
        String sql =
                """
                SELECT id, nombre, activo
                FROM area
                WHERE id = ?;
                """;

        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            try (ResultSet rs = cmd.executeQuery()) {
                if (rs.next()) {
                    Area area = new Area();
                    area.setId(rs.getInt("id"));
                    area.setNombre(rs.getString("nombre"));
                    area.setActivo(rs.getBoolean("activo"));
                    return area;
                }
                else {
                    return null;
                }
            }
        }
    }

    @Override
    public void insert(Area area) throws SQLException {
        String sql =
                """
                INSERT INTO area (nombre, activo) 
                VALUES (?, ?);
                """;

        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            cmd.setString(1, area.getNombre());
            cmd.setBoolean(2, area.isActivo());

            if (cmd.executeUpdate() == 0) {;
                throw new SQLException("Error al insertar el area.");
            }

            try (ResultSet generatedKeys = cmd.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    area.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Error al insertar el area, no se obtuvo el ID.");
                }
            }
        }
    }

    @Override
    public void update(Area area) throws SQLException {
        String sql =
                """
                UPDATE area
                SET nombre = ?, activo = ?
                WHERE id = ?;
                """;

        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setString(1, area.getNombre());
            cmd.setBoolean(2, area.isActivo());
            cmd.setInt(3, area.getId());

            if (cmd.executeUpdate() == 0) {;
                throw new SQLException("Error al actualizar el area.");
            }
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql =
                """
                DELETE FROM area
                WHERE id = ?;
                """;

        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);
            cmd.executeUpdate();
        }
    }
}
