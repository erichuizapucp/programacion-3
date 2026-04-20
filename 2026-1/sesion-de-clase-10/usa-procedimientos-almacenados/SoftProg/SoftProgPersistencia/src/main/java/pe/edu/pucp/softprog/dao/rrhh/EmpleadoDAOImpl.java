package pe.edu.pucp.softprog.dao.rrhh;

import pe.edu.pucp.softprog.dao.PersonaBaseDAO;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class EmpleadoDAOImpl extends PersonaBaseDAO<Empleado> implements EmpleadoDAO {

    protected PreparedStatement comandoCrear(Connection conn,
                                             Empleado modelo) throws SQLException {
        String sql = "{call insertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idArea", modelo.getArea().getId());

        Integer idCuentaUsuario = getIdCuentaUsuario(modelo);
        if (idCuentaUsuario == null) {
            cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
        }
        else {
            cmd.setInt("p_idCuentaUsuario", idCuentaUsuario);
        }

        cmd.setString("p_dni", modelo.getDni());
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_apellidoPaterno", modelo.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(modelo.getGenero()));
        cmd.setDate("p_fechaNacimiento", new java.sql.Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString("p_cargo", String.valueOf(modelo.getCargo()));
        cmd.setDouble("p_sueldo", modelo.getSueldo());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
                                                  Empleado modelo) throws SQLException {
        String sql = "{call modificarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idArea", modelo.getArea().getId());

        Integer idCuentaUsuario = getIdCuentaUsuario(modelo);
        if (idCuentaUsuario == null) {
            cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
        }
        else {
            cmd.setInt("p_idCuentaUsuario", idCuentaUsuario);
        }

        cmd.setString("p_dni", modelo.getDni());
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_apellidoPaterno", modelo.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(modelo.getGenero()));
        cmd.setDate("p_fechaNacimiento", new java.sql.Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString("p_cargo", String.valueOf(modelo.getCargo()));
        cmd.setDouble("p_sueldo", modelo.getSueldo());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = "{call eliminarEmpleado(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = "{call buscarEmpleadoPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "{call listarEmpleados()}";
        return conn.prepareCall(sql);
    }

    @Override
    protected PreparedStatement comandoBuscarPorDni(Connection conn,
                                                    String dni) throws SQLException {
        String sql = "{call buscarEmpleadoPorDni(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_dni", dni);
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
