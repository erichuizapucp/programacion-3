package pe.edu.pucp.softprog.dao.clientes;

import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    @Override
    public Integer crear(Cliente modelo) {
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
    public boolean actualizar(Cliente modelo) {
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
    public Cliente leer(int id) {
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
    public List<Cliente> leerTodos() {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoLeerTodos(conn)) {
            ResultSet rs = cmd.executeQuery();
            List<Cliente> modelos = new ArrayList<>();
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
    public Cliente buscarPorDni(String dni) {
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
                                             Cliente modelo) throws SQLException {
        String sql = """
            INSERT INTO CLIENTE (
                idCuentaUsuario,
                dni,
                nombre,
                apellidoPaterno,
                genero,
                fechaNacimiento,
                categoria,
                lineaCredito,
                activo
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        PreparedStatement cmd = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        if (modelo.getCuentaUsuario() == null) {
            cmd.setNull(1, java.sql.Types.INTEGER);
        }
        else {
            cmd.setInt(1, modelo.getCuentaUsuario().getId());
        }
        cmd.setString(2, modelo.getDni());
        cmd.setString(3, modelo.getNombre());
        cmd.setString(4, modelo.getApellidoPaterno());
        cmd.setString(5, modelo.getGenero().name());
        cmd.setDate(6, new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString(7, modelo.getCategoria().name());
        cmd.setDouble(8, modelo.getLineaCredito());
        cmd.setBoolean(9, modelo.isActivo());
        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
                                                  Cliente modelo) throws SQLException {
        String sql = """
            UPDATE CLIENTE
            SET idCuentaUsuario = ?,
                dni = ?,
                nombre = ?,
                apellidoPaterno = ?,
                genero = ?,
                fechaNacimiento = ?,
                categoria = ?,
                lineaCredito = ?,
                activo = ?
            WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        if (modelo.getCuentaUsuario() == null) {
            cmd.setNull(1, java.sql.Types.INTEGER);
        }
        else {
            cmd.setInt(1, modelo.getCuentaUsuario().getId());
        }
        cmd.setString(2, modelo.getDni());
        cmd.setString(3, modelo.getNombre());
        cmd.setString(4, modelo.getApellidoPaterno());
        cmd.setString(5, modelo.getGenero().name());
        cmd.setDate(6, new Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString(7, modelo.getCategoria().name());
        cmd.setDouble(8, modelo.getLineaCredito());
        cmd.setBoolean(9, modelo.isActivo());
        cmd.setInt(10, modelo.getId());
        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = "DELETE FROM CLIENTE WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = "SELECT * FROM CLIENTE WHERE id = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "SELECT * FROM CLIENTE";
        return conn.prepareStatement(sql);
    }

    protected PreparedStatement comandoBuscarPorDni(Connection conn,
                                                    String dni) throws SQLException {
        String sql = "SELECT * FROM CLIENTE WHERE dni = ?";
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, dni);
        return cmd;
    }

    protected Cliente mapearModelo(ResultSet rs) throws SQLException {
        Cliente modelo = new Cliente();
        modelo.setId(rs.getInt("id"));

        int idCuentaUsuario = rs.getInt("idCuentaUsuario");
        if (!rs.wasNull()) {
            modelo.setCuentaUsuario(new CuentaUsuarioDAOImpl().leer(idCuentaUsuario));
        }

        modelo.setDni(rs.getString("dni"));
        modelo.setNombre(rs.getString("nombre"));
        modelo.setApellidoPaterno(rs.getString("apellidoPaterno"));
        modelo.setGenero(Genero.valueOf(rs.getString("genero")));
        modelo.setFechaNacimiento(rs.getDate("fechaNacimiento"));
        modelo.setCategoria(CategoriaCliente.valueOf(rs.getString("categoria")));
        modelo.setLineaCredito(rs.getDouble("lineaCredito"));
        modelo.setActivo(rs.getBoolean("activo"));
        return modelo;
    }
}
