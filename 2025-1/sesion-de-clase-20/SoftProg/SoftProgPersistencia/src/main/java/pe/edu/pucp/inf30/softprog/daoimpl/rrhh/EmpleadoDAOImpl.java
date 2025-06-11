package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.dao.rrhh.IEmpleadoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAOImpl;
import pe.edu.pucp.inf30.softprog.model.rrhh.Empleado;

public class EmpleadoDAOImpl extends BaseDAOImpl<Empleado> implements IEmpleadoDAO {    
    @Override
    protected CallableStatement comandoInsertar(Connection conn, Empleado empleado) throws SQLException {
        String sql = "{CALL insertarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);   
        cmd.setInt("p_idArea", empleado.getArea().getId());
        
        if (empleado.getCuentaUsuario() != null) {
            cmd.setInt("p_idCuentaUsuario", empleado.getCuentaUsuario().getId());
        }
        else {
            cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
        }
        
        cmd.setString("p_dni", empleado.getDni());
        cmd.setString("p_nombre", empleado.getNombre());
        cmd.setString("p_apellidoPaterno", empleado.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(empleado.getGenero()));
        cmd.setDate("p_fechaNacimiento", new Date(empleado.getFechaNacimiento().getTime()));
        cmd.setString("p_cargo", empleado.getCargo());
        cmd.setDouble("p_sueldo", empleado.getSueldo());
        cmd.setBoolean("p_activo", empleado.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }
    
    @Override
    protected CallableStatement comandoModificar(Connection conn, Empleado empleado) throws SQLException {
        String sql = "{CALL modificarEmpleado(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idArea", empleado.getArea().getId());
        
        if (empleado.getCuentaUsuario() != null) {
            cmd.setInt("p_idCuentaUsuario", empleado.getCuentaUsuario().getId());
        }
        else {
            cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
        }
        
        cmd.setString("p_dni", empleado.getDni());
        cmd.setString("p_nombre", empleado.getNombre());
        cmd.setString("p_apellidoPaterno", empleado.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(empleado.getGenero()));
        cmd.setDate("p_fechaNacimiento", new Date(empleado.getFechaNacimiento().getTime()));
        cmd.setString("p_cargo", empleado.getCargo());
        cmd.setDouble("p_sueldo", empleado.getSueldo());
        cmd.setBoolean("p_activo", empleado.isActivo());
        cmd.setInt("p_id", empleado.getId());
        return cmd;
    }

    @Override
    protected CallableStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "{CALL eliminarEmpleado(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "{CALL buscarEmpleadoPorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoListar(Connection conn) throws SQLException {
        String sql = "{CALL listarEmpleados()}";
        CallableStatement cmd = conn.prepareCall(sql);
        return cmd;
    }

    @Override
    protected Empleado mapearModelo(ResultSet rs) throws SQLException {
        Empleado empleado = new Empleado();
        empleado.setId(rs.getInt("id"));
        empleado.setArea(new AreaDAOImpl().buscar(rs.getInt("idArea")));
        //empleado.setCuentaUsuario(new CuentaUsuarioDAOImpl().buscar(rs.getInt("idCuentaUsuario")));
        empleado.setDni(rs.getString("dni"));
        empleado.setNombre(rs.getString("nombre"));
        empleado.setApellidoPaterno(rs.getString("apellidoPaterno"));
        empleado.setGenero(rs.getString("genero").charAt(0));
        empleado.setFechaNacimiento(rs.getTimestamp("fechaNacimiento"));
        empleado.setCargo(rs.getString("cargo"));
        empleado.setSueldo(rs.getDouble("sueldo"));
        empleado.setActivo(rs.getBoolean("activo"));

        return empleado;
    }

    protected CallableStatement comandoBuscarPorDni(Connection conn, String dni) throws SQLException {
        String sql = "{CALL buscarEmpleadoPorDni(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_dni", dni);
        return cmd;
    }
    
    @Override
    public Empleado buscarPorDni(String dni) {
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = this.comandoBuscarPorDni(conn, dni);
        ) {
            ResultSet rs = cmd.executeQuery();
            
            if (!rs.next()) {
                System.err.println("No se encontro el registro con dni: " + dni);
                return null;
            }
            
            return this.mapearModelo(rs);
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la insercion: " + e.getMessage());
            throw new RuntimeException("No se pudo insertar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al insertar el registro.", e);
        }
    }
}
