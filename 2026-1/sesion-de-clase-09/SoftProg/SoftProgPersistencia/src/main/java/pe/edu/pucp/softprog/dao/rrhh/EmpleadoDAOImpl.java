package pe.edu.pucp.softprog.dao.rrhh;

import pe.edu.pucp.softprog.dao.PersonaBaseDAO;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmpleadoDAOImpl extends PersonaBaseDAO<Empleado> implements EmpleadoDAO {

    protected PreparedStatement comandoCrear(Connection conn,
                                             Empleado modelo) throws SQLException {
        String sql = """
            INSERT INTO EMPLEADO (
                idArea,
                idCuentaUsuario,
                dni,
                nombre,
                apellidoPaterno,
                genero,
                fechaNacimiento,
                cargo,
                sueldo,
                activo
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        setCamposEmpleado(cmd, modelo);
        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
                                                  Empleado modelo) throws SQLException {
        String sql = """
            UPDATE EMPLEADO
            SET idArea = ?,
                idCuentaUsuario = ?,
                dni = ?,
                nombre = ?,
                apellidoPaterno = ?,
                genero = ?,
                fechaNacimiento = ?,
                cargo = ?,
                sueldo = ?,
                activo = ?
            WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        int nextIndex = setCamposEmpleado(cmd, modelo);
        cmd.setInt(nextIndex, modelo.getId());
        return cmd;
    }

    private int setCamposEmpleado(PreparedStatement cmd, Empleado modelo) throws SQLException {
        cmd.setInt(1, modelo.getArea().getId());
        setCuentaUsuarioNullable(cmd, 2, modelo);
        int idx = setCamposPersona(cmd, 3, modelo);
        cmd.setString(idx, modelo.getCargo().name());
        cmd.setDouble(idx + 1, modelo.getSueldo());
        cmd.setBoolean(idx + 2, modelo.isActivo());
        return idx + 3;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = "DELETE FROM EMPLEADO WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = "SELECT * FROM EMPLEADO WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = """
            SELECT * FROM EMPLEADO
            """;
        return conn.prepareStatement(sql);
    }

    @Override
    protected PreparedStatement comandoBuscarPorDni(Connection conn,
                                                    String dni) throws SQLException {
        String sql = """
            SELECT * FROM EMPLEADO WHERE dni = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, dni);
        return cmd;
    }

    @Override
    protected Empleado mapearModelo(ResultSet rs) throws SQLException {
        Empleado modelo = new Empleado();
        modelo.setId(rs.getInt("id"));
        modelo.setArea(new AreaDAOImpl().leer(rs.getInt("idArea")));

        Integer idCuentaUsuario = leerIdCuentaUsuario(rs);
        if (idCuentaUsuario != null) {
            modelo.setCuentaUsuario(new CuentaUsuarioDAOImpl().leer(idCuentaUsuario));
        }

        mapearCamposPersona(rs, modelo);
        modelo.setCargo(Cargo.valueOf(rs.getString("cargo")));
        modelo.setSueldo(rs.getDouble("sueldo"));
        modelo.setActivo(rs.getBoolean("activo"));
        return modelo;
    }
}
