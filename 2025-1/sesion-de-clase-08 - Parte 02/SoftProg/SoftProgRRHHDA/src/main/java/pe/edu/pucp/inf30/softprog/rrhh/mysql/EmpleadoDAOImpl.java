package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IEmpleadoDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.Empleado;

public class EmpleadoDAOImpl extends BaseDAOImpl<Empleado> implements IEmpleadoDAO {
    @Override
    protected PreparedStatement comandoInsertar(Connection conn, Empleado empleado) throws SQLException {
        String sql = ""
                + "INSERT EMPLEADO("
                +   "idArea, "
                +   "idCuentaUsuario, "
                +   "dni, "
                +   "nombre, "
                +   "apellidoPaterno, "
                +   "genero, "
                +   "fechaNacimiento, "
                +   "cargo, "
                +   "sueldo, "
                +   "activo) "
                + "VALUES"
                +   "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);   
        cmd.setInt(1, empleado.getArea().getId());
        cmd.setInt(2, empleado.getCuentaUsuario().getId());
        cmd.setString(3, empleado.getDni());
        cmd.setString(4, empleado.getNombre());
        cmd.setString(5, empleado.getApellidoPaterno());
        cmd.setString(6, String.valueOf(empleado.getGenero()));
        cmd.setDate(7, new Date(empleado.getFechaNacimiento().getTime()));
        cmd.setString(8, empleado.getCargo());
        cmd.setDouble(9, empleado.getSueldo());
        cmd.setBoolean(10, empleado.isActivo());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoModificar(Connection conn, Empleado empleado) throws SQLException {
        String sql = ""
                + "UPDATE EMPLEADO "
                + "SET idArea = ?, "
                    + "idCuentaUsuario = ?, "
                    + "dni = ?, "
                    + "nombre = ?, "
                    + "apellidoPaterno = ?, "
                    + "genero = ?, "
                    + "fechaNacimiento = ?, "
                    + "cargo = ?, "
                    + "sueldo = ?, "
                    + "activo = ? "
                + "WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, empleado.getArea().getId());
        cmd.setInt(2, empleado.getCuentaUsuario().getId());
        cmd.setString(3, empleado.getDni());
        cmd.setString(4, empleado.getNombre());
        cmd.setString(5, empleado.getApellidoPaterno());
        cmd.setString(6, String.valueOf(empleado.getGenero()));
        cmd.setDate(7, new Date(empleado.getFechaNacimiento().getTime()));
        cmd.setString(8, empleado.getCargo());
        cmd.setDouble(9, empleado.getSueldo());
        cmd.setBoolean(10, empleado.isActivo());
        cmd.setInt(11, empleado.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM EMPLEADO WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM EMPLEADO WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoListar(Connection conn) throws SQLException {
        String sql = "SELECT * FROM EMPLEADO";
        PreparedStatement cmd = conn.prepareStatement(sql);
        return cmd;
    }

    @Override
    protected Empleado mapearModelo(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getInt("id"));
        empleado.setArea(new AreaDAOImpl().buscar(rs.getInt("idArea")));
        empleado.setCuentaUsuario(new CuentaUsuarioDAOImpl().buscar(rs.getInt("idCuentaUsuario")));
        empleado.setDni(rs.getString("dni"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellidoPaterno(rs.getString("apellidoPaterno"));
        empleado.setGenero(rs.getString("genero").charAt(0));
        empleado.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        empleado.setCargo(rs.getString("cargo"));
        empleado.setSueldo(rs.getDouble("sueldo"));
        empleado.setActivo(rs.getBoolean("activo"));

        return empleado;
    }
}
