package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IEmpleadoDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.Empleado;

public class EmpleadoDAOImpl implements IEmpleadoDAO {
    @Override
    public int insertar(Empleado empleado) {
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
        
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setInt(1, empleado.getArea().getId());
            ps.setInt(2, empleado.getCuentaUsuario().getId());
            ps.setString(3, empleado.getDni());
            ps.setString(4, empleado.getNombre());
            ps.setString(5, empleado.getApellidoPaterno());
            ps.setString(6, String.valueOf(empleado.getGenero())); // Convierte un char en cadena
            
            // Convierte un java.util.Date en un java.sql.Date
            // Uso el nombre completo java.sql.Date para que se observe que se usa esa clase y no java.util.Date para 
            // el comando
            java.sql.Date fechaNacimiento = new java.sql.Date(empleado.getFechaNacimiento().getTime());
            ps.setDate(7, fechaNacimiento);
            //ps.setDate(7, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            
            ps.setString(8, empleado.getCargo());
            ps.setDouble(9, empleado.getSueldo());
            ps.setBoolean(10, empleado.isActivo());
            
            if (ps.executeUpdate() == 0) {
                System.err.println("El registro no se inserto.");
                return 0;
            }
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : -1;
            }
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

    @Override
    public boolean modificar(Empleado empleado) {
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
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, empleado.getArea().getId());
            ps.setInt(2, empleado.getCuentaUsuario().getId());
            ps.setString(3, empleado.getDni());
            ps.setString(4, empleado.getNombre());
            ps.setString(5, empleado.getApellidoPaterno());
            ps.setString(6, String.valueOf(empleado.getGenero()));
            ps.setDate(7, new java.sql.Date(empleado.getFechaNacimiento().getTime()));
            ps.setString(8, empleado.getCargo());
            ps.setDouble(9, empleado.getSueldo());
            ps.setBoolean(10, empleado.isActivo());
            ps.setInt(11, empleado.getId());
            
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la modificacion: " + e.getMessage());
            throw new RuntimeException("No se pudo modificar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al modificar el registro.", e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM EMPLEADO WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            return ps.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la eliminacion: " + e.getMessage());
            throw new RuntimeException("No se pudo eliminar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al eliminar el registro.", e);
        }
    }

    @Override
    public Empleado buscar(int id) {
        String sql = "SELECT * FROM EMPLEADO WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    System.err.println("No se encontro el empleado con id: " + id);
                    return null;
                }

                Empleado empleado = new Empleado();
                empleado.setId(rs.getInt("id"));
                empleado.setArea(new AreaDAOImpl().buscar(rs.getInt("idArea")));
                empleado.setCuentaUsuario(new CuentaUsuarioDAOImpl().buscar(rs.getInt("idCuentaUsuario")));
                empleado.setDni(rs.getString("dni"));
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellidoPaterno(rs.getString("apellidoPaterno"));
                empleado.setGenero(rs.getString("genero").charAt(0));
                // Cuando una fecha viene de la base de datos no es necesario
                // convertir java.sql.Date a java.util.Date
                // JDBC lo maneja automaticamente
                empleado.setFechaNacimiento(rs.getDate("fechaNacimiento"));
                empleado.setCargo(rs.getString("cargo"));
                empleado.setSueldo(rs.getDouble("sueldo"));
                empleado.setActivo(rs.getBoolean("activo"));

                return empleado;
            }
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la busqueda: " + e.getMessage());
            throw new RuntimeException("No se pudo buscar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al buscar el registro.", e);
        }
    }

    @Override
    public List<Empleado> listar() {
        String sql = "SELECT * FROM EMPLEADO";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {
            
            List<Empleado> empleados = new ArrayList<>();
            while (rs.next()) {
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
                
                empleados.add(empleado);
            }
            
            return empleados;
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante el listado: " + e.getMessage());
            throw new RuntimeException("No se pudo listar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al listar los registros.", e);
        }
    }
}
