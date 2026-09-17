package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.EmpleadoDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl extends PersonaDAOImpl<Empleado> implements EmpleadoDAO {
    @Override
    public List<Empleado> findAll() throws SQLException {
        String sql = "{call listar_empleados()}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql);
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

        String sql = "{call buscar_empleado_por_id(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Empleado()) : null;
            }
        }
    }

    @Override
    public Empleado findByDni(String dni) throws SQLException {
        if (dni == null) {
            throw new IllegalArgumentException("El dni no puede ser nulo");
        }

        String sql = "{call buscar_empleado_por_dni(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_dni", dni);

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

        String sql = "{call insertar_empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id_area", empleado.getArea().getId());
            cmd.setInt("p_id_cuenta_usuario", empleado.getCuentaUsuario().getId());
            cmd.setString("p_dni", empleado.getDni());
            cmd.setString("p_nombre", empleado.getNombre());
            cmd.setString("p_apellido_paterno", empleado.getApellidoPaterno());
            cmd.setString("p_genero", empleado.getGenero().name());
            cmd.setDate("p_fecha_nacimiento", Date.valueOf(empleado.getFechaNacimiento()));
            cmd.setString("p_cargo", empleado.getCargo().name());
            cmd.setDouble("p_sueldo", empleado.getSueldo());
            cmd.setBoolean("p_activo", empleado.isActivo());
            cmd.registerOutParameter("p_id", Types.INTEGER);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el empleado");
            }

            empleado.setId(cmd.getInt("p_id"));
        }
    }

    @Override
    public void update(Empleado empleado) throws SQLException {
        if (empleado == null) {
            throw new IllegalArgumentException("El empleado no puede ser nulo");
        }

        String sql = "{call modificar_empleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id_area", empleado.getArea().getId());
            cmd.setInt("p_id_cuenta_usuario", empleado.getCuentaUsuario().getId());
            cmd.setString("p_dni", empleado.getDni());
            cmd.setString("p_nombre", empleado.getNombre());
            cmd.setString("p_apellido_paterno", empleado.getApellidoPaterno());
            cmd.setString("p_genero", empleado.getGenero().name());
            cmd.setDate("p_fecha_nacimiento", Date.valueOf(empleado.getFechaNacimiento()));
            cmd.setString("p_cargo", empleado.getCargo().name());
            cmd.setDouble("p_sueldo", empleado.getSueldo());
            cmd.setBoolean("p_activo", empleado.isActivo());
            cmd.setInt("p_id", empleado.getId());

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

        String sql = "{call eliminar_empleado(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar el empleado");
            }
        }
    }

    @Override
    protected Empleado mapear(ResultSet rs, Empleado empleado) throws SQLException {
        super.mapear(rs, empleado);
        empleado.setCargo(Enum.valueOf(Cargo.class, rs.getString("cargo")));
        empleado.setSueldo(rs.getDouble("sueldo"));
        empleado.setArea(new AreaDAOImpl().findById(rs.getInt("id_area")));
        return empleado;
    }
}
