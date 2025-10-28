package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.rrhh.EmpleadoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAO;
import pe.edu.pucp.inf30.softprog.modelo.Genero;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;

/**
 *
 * @author eric
 */
public class EmpleadoDAOImpl extends BaseDAO<Empleado> implements EmpleadoDAO {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, Empleado modelo) 
            throws SQLException {
        
        String sql = "{call insertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);   
        cmd.setInt("p_idArea", modelo.getArea().getId());
        
        if (modelo.getCuentaUsuario() != null) {
            cmd.setInt("p_idCuentaUsuario", modelo.getCuentaUsuario().getId());
        }
        else {
            cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
        }
        
        cmd.setString("p_dni", modelo.getDni());
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_apellidoPaterno", modelo.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(modelo.getGenero()));
        cmd.setDate("p_fechaNacimiento", 
                new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString("p_cargo", String.valueOf(modelo.getCargo()));
        cmd.setDouble("p_sueldo", modelo.getSueldo());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            Empleado modelo) throws SQLException {
        
        String sql = 
                "{call modificarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idArea", modelo.getArea().getId());
        
        if (modelo.getCuentaUsuario() != null) {
            cmd.setInt("p_idCuentaUsuario", modelo.getCuentaUsuario().getId());
        }
        else {
            cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
        }
        
        cmd.setString("p_dni", modelo.getDni());
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_apellidoPaterno", modelo.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(modelo.getGenero()));
        cmd.setDate("p_fechaNacimiento", 
                new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString("p_cargo", String.valueOf(modelo.getCargo()));
        cmd.setDouble("p_sueldo", modelo.getSueldo());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) 
            throws SQLException {
        
        String sql = "{call eliminarEmpleado(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) 
            throws SQLException {
        
        String sql = "{call buscarEmpleadoPorId(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException {
        
        String sql = "{call listarEmpleados()}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        
        return cmd;
    }

    @Override
    protected Empleado mapearModelo(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getInt("id"));
        empleado.setArea(new AreaDAOImpl().leer(rs.getInt("idArea")));
        
        int idCuentaUsuario = rs.getInt("idCuentaUsuario");
        if (!rs.wasNull()) {
            empleado.setCuentaUsuario(
                    new CuentaUsuarioDAOImpl().leer(idCuentaUsuario));
        }
        empleado.setDni(rs.getString("dni"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellidoPaterno(rs.getString("apellidoPaterno"));
        empleado.setGenero(Genero.valueOf(rs.getString("genero")));
        empleado.setFechaNacimiento(rs.getTimestamp("fechaNacimiento"));
        empleado.setCargo(Cargo.valueOf(rs.getString("cargo")));
        empleado.setSueldo(rs.getDouble("sueldo"));
        empleado.setActivo(rs.getBoolean("activo"));

        return empleado;
    }

    protected PreparedStatement comandoBuscarPorDni(Connection conn, String dni)
        throws SQLException {
        String sql = "{call buscarEmpleadoPorDni(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_dni", dni);
        
        return cmd;
    }
    
    @Override
    public Empleado buscarPorDni(String dni) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoBuscarPorDni(conn, dni)) {
                ResultSet rs = cmd.executeQuery();

                if (!rs.next()) {
                    System.err.println("No se encontro el registro con "
                            + "dni: " + dni);
                    return null;
                }

                return this.mapearModelo(rs);
            }
        });
    }
}
