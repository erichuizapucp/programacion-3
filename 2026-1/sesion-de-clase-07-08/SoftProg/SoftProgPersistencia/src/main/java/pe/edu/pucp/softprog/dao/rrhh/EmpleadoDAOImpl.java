package pe.edu.pucp.softprog.dao.rrhh;

import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAOImpl implements EmpleadoDAO {
    @Override
    public Integer crear(Empleado modelo) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoCrear(conn, modelo)) {
            if (cmd.executeUpdate() == 0) {
                return null;
            }
            try (ResultSet rs = cmd.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean actualizar(Empleado modelo) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoActualizar(conn, modelo)) {
            return cmd.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean eliminar(int id) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoEliminar(conn, id)) {
            return cmd.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Empleado leer(int id) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLeer(conn, id)) {
            ResultSet rs = cmd.executeQuery();
            if (!rs.next()) {
                System.err.println("No se encontro el registro con id: " + id);
                return null;
            }
            return this.mapearModelo(rs);
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Empleado> leerTodos() {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLeerTodos(conn)) {
            ResultSet rs = cmd.executeQuery();
            List<Empleado> modelos = new ArrayList<>();
            while (rs.next()) {
                modelos.add(this.mapearModelo(rs));
            }
            return modelos;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public Empleado buscarPorDni(String dni) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoBuscarPorDni(conn, dni)) {
            ResultSet rs = cmd.executeQuery();
            return rs.next() ? this.mapearModelo(rs) : null;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

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
        cmd.setInt(1, modelo.getArea().getId());
        if (modelo.getCuentaUsuario() == null) {
            cmd.setNull(2, java.sql.Types.INTEGER);
        }
        else {
            cmd.setInt(2, modelo.getCuentaUsuario().getId());
        }
        cmd.setString(3, modelo.getDni());
        cmd.setString(4, modelo.getNombre());
        cmd.setString(5, modelo.getApellidoPaterno());
        cmd.setString(6, modelo.getGenero().name());
        cmd.setDate(7, new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString(8, modelo.getCargo().name());
        cmd.setDouble(9, modelo.getSueldo());
        cmd.setBoolean(10, modelo.isActivo());
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
        cmd.setInt(1, modelo.getArea().getId());
        if (modelo.getCuentaUsuario() == null) {
            cmd.setNull(2, java.sql.Types.INTEGER);
        }
        else {
            cmd.setInt(2, modelo.getCuentaUsuario().getId());
        }
        cmd.setString(3, modelo.getDni());
        cmd.setString(4, modelo.getNombre());
        cmd.setString(5, modelo.getApellidoPaterno());
        cmd.setString(6, modelo.getGenero().name());
        cmd.setDate(7, new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString(8, modelo.getCargo().name());
        cmd.setDouble(9, modelo.getSueldo());
        cmd.setBoolean(10, modelo.isActivo());
        cmd.setInt(11, modelo.getId());
        return cmd;
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
        String sql = "SELECT * FROM EMPLEADO";
        return conn.prepareStatement(sql);
    }

    protected PreparedStatement comandoBuscarPorDni(Connection conn,
                                                    String dni) throws SQLException {
        String sql = "SELECT * FROM EMPLEADO WHERE dni = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, dni);
        return cmd;
    }

    protected Empleado mapearModelo(ResultSet rs) throws SQLException {
        Empleado modelo = new Empleado();
        modelo.setId(rs.getInt("id"));
        modelo.setArea(new AreaDAOImpl().leer(rs.getInt("idArea")));

        int idCuentaUsuario = rs.getInt("idCuentaUsuario");
        if (!rs.wasNull()) {
            modelo.setCuentaUsuario(new CuentaUsuarioDAOImpl().leer(idCuentaUsuario));
        }

        modelo.setDni(rs.getString("dni"));
        modelo.setNombre(rs.getString("nombre"));
        modelo.setApellidoPaterno(rs.getString("apellidoPaterno"));
        modelo.setGenero(Genero.valueOf(rs.getString("genero")));
        modelo.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        modelo.setCargo(Cargo.valueOf(rs.getString("cargo")));
        modelo.setSueldo(rs.getDouble("sueldo"));
        modelo.setActivo(rs.getBoolean("activo"));
        return modelo;
    }
}
