package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
        
        String sql = 
                "INSERT INTO EMPLEADO ("
                + " idArea, "
                + " idCuentaUsuario, "
                + " dni, "
                + " nombre, "
                + " apellidoPaterno, "
                + " genero, "
                + " fechaNacimiento, "
                + " cargo, "
                + " sueldo, "
                + " activo) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement cmd = conn.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS);   
        cmd.setInt(1, modelo.getArea().getId());
        
        if (modelo.getCuentaUsuario() != null) {
            cmd.setInt(2, modelo.getCuentaUsuario().getId());
        }
        else {
            cmd.setNull(2, Types.INTEGER);
        }
        
        cmd.setString(3, modelo.getDni());
        cmd.setString(4, modelo.getNombre());
        cmd.setString(5, modelo.getApellidoPaterno());
        cmd.setString(6, String.valueOf(modelo.getGenero()));
        cmd.setDate(7, new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString(8, String.valueOf(modelo.getCargo()));
        cmd.setDouble(9, modelo.getSueldo());
        cmd.setBoolean(10, modelo.isActivo());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            Empleado modelo) throws SQLException {
        
        String sql = 
                "UPDATE EMPLEADO "
                + "SET "
                + " idArea = ?, "
                + " idCuentaUsuario = ?, "
                + " dni = ?, "
                + " nombre = ?, "
                + " apellidoPaterno = ?, "
                + " genero = ?, "
                + " fechaNacimiento = ?, "
                + " cargo = ?, "
                + " sueldo = ?, "
                + " activo = ? "
                + "WHERE "
                + " id = ?";
        
        PreparedStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, modelo.getArea().getId());
        
        if (modelo.getCuentaUsuario() != null) {
            cmd.setInt(2, modelo.getCuentaUsuario().getId());
        }
        else {
            cmd.setNull(2, Types.INTEGER);
        }
        
        cmd.setString(3, modelo.getDni());
        cmd.setString(4, modelo.getNombre());
        cmd.setString(5, modelo.getApellidoPaterno());
        cmd.setString(6, String.valueOf(modelo.getGenero()));
        cmd.setDate(7, new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString(8, String.valueOf(modelo.getCargo()));
        cmd.setDouble(9, modelo.getSueldo());
        cmd.setBoolean(10, modelo.isActivo());
        cmd.setInt(11, modelo.getId());
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) 
            throws SQLException {
        
        String sql = 
                "DELETE "
                + "FROM EMPLEADO "
                + "WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) 
            throws SQLException {
        
        String sql = 
                "SELECT "
                + " id, "
                + " idArea, "
                + " idCuentaUsuario, "
                + " dni, "
                + " nombre, "
                + " apellidoPaterno, "
                + " genero, "
                + " fechaNacimiento, "
                + " cargo, "
                + " sueldo, "
                + " activo "
                + "FROM EMPLEADO "
                + "WHERE id = ?";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException {
        
        String sql = 
                "SELECT "
                + " id, "
                + " idArea, "
                + " idCuentaUsuario, "
                + " dni, "
                + " nombre, "
                + " apellidoPaterno, "
                + " genero, "
                + " fechaNacimiento, "
                + " cargo, "
                + " sueldo, "
                + " activo "
                + "FROM EMPLEADO";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        
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
}
