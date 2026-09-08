package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.EmpleadoDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    @Override
    public List<Empleado> findAll() throws SQLException {
        String sql =
                """
                SELECT
                    id,
                    id_area,
                    id_cuenta_usuario,
                    dni,
                    nombre,
                    apellido_paterno,
                    genero,
                    fecha_nacimiento,
                    cargo,
                    sueldo,
                    activo
                FROM empleado
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery()) {

            List<Empleado> empleados = new ArrayList<>();
            while (rs.next()) {
                empleados.add(mapear(rs, new Empleado()));
            }
            return empleados;
        }
    }

    @Override
    public Empleado findById(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql =
                """
                SELECT
                    id,
                    id_area,
                    id_cuenta_usuario,
                    dni,
                    nombre,
                    apellido_paterno,
                    genero,
                    fecha_nacimiento,
                    cargo,
                    sueldo,
                    activo
                FROM empleado WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Empleado()) : null;
            }
        }
    }

    @Override
    public void insert(Empleado empleado) throws SQLException {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nula");
        }

        String sql =
                """
                INSERT INTO empleado (
                  id_area,
                  id_cuenta_usuario,
                  dni,
                  nombre,
                  apellido_paterno,
                  genero,
                  fecha_nacimiento,
                  cargo,
                  sueldo,
                  activo)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            cmd.setInt(1, empleado.getArea().getId());
            cmd.setInt(2, empleado.getCuentaUsuario().getId());
            cmd.setString(3, empleado.getDni());
            cmd.setString(4, empleado.getNombre());
            cmd.setString(5, empleado.getApellidoPaterno());
            cmd.setString(6, empleado.getGenero().name());
            cmd.setDate(7, Date.valueOf(empleado.getFechaNacimiento()));
            cmd.setString(8, empleado.getCargo().name());
            cmd.setDouble(9, empleado.getSueldo());
            cmd.setBoolean(10, empleado.isActivo());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el empleado");
            }

            try (ResultSet rs = cmd.getGeneratedKeys()) {
                if (rs.next()) {
                    empleado.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Empleado empleado) throws SQLException {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }

        String sql =
                """
                UPDATE empleado
                SET
                    id_area = ?,
                    id_cuenta_usuario = ?,
                    dni = ?,
                    nombre = ?,
                    apellido_paterno = ?,
                    genero = ?,
                    fecha_nacimiento = ?,
                    cargo = ?,
                    sueldo = ?,
                    activo = ?
                WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, empleado.getArea().getId());
            cmd.setInt(2, empleado.getCuentaUsuario().getId());
            cmd.setString(3, empleado.getDni());
            cmd.setString(4, empleado.getNombre());
            cmd.setString(5, empleado.getApellidoPaterno());
            cmd.setString(6, empleado.getGenero().name());
            cmd.setDate(7, Date.valueOf(empleado.getFechaNacimiento()));
            cmd.setString(8, empleado.getCargo().name());
            cmd.setDouble(9, empleado.getSueldo());
            cmd.setBoolean(10, empleado.isActivo());
            cmd.setInt(11, empleado.getId());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar el empleado");
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
                DELETE FROM empleado WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar el empleado");
            }
        }
    }

    private Empleado mapear(ResultSet rs, Empleado empleado) throws SQLException {
        empleado.setId(rs.getInt("id"));
        empleado.setActivo(rs.getBoolean("activo"));

        empleado.setDni(rs.getString("dni"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellidoPaterno(rs.getString("apellido_paterno"));
        empleado.setGenero(Enum.valueOf(Genero.class, rs.getString("genero")));
        empleado.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());

        int idCuentaUsuario = rs.getInt("id_cuenta_usuario");
        if (!rs.wasNull()) {
            empleado.setCuentaUsuario(new CuentaUsuarioDAOImpl().findById(idCuentaUsuario));
        }
        else {
            empleado.setCuentaUsuario(null);
        }

        empleado.setCargo(Enum.valueOf(Cargo.class, rs.getString("cargo")));
        empleado.setSueldo(rs.getDouble("sueldo"));
        empleado.setArea(new AreaDAOImpl().findById(rs.getInt("id_area")));
        return empleado;
    }
}
