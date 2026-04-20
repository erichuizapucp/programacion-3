package pe.edu.pucp.softprog.dao.clientes;

import pe.edu.pucp.softprog.dao.PersonaBaseDAO;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ClienteDAOImpl extends PersonaBaseDAO<Cliente> implements ClienteDAO {

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
        setCamposCliente(cmd, modelo);
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
        int nextIndex = setCamposCliente(cmd, modelo);
        cmd.setInt(nextIndex, modelo.getId());
        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = """
            DELETE FROM CLIENTE WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = """
            SELECT * FROM CLIENTE WHERE id = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = """
            SELECT * FROM CLIENTE
            """;
        return conn.prepareStatement(sql);
    }

    @Override
    protected PreparedStatement comandoBuscarPorDni(Connection conn,
                                                    String dni) throws SQLException {
        String sql = """
            SELECT * FROM CLIENTE WHERE dni = ?
            """;
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, dni);
        return cmd;
    }

    protected Cliente mapearModelo(ResultSet rs) throws SQLException {
        Cliente modelo = new Cliente();
        modelo.setId(rs.getInt("id"));

        Integer idCuentaUsuario = leerIdCuentaUsuario(rs);
        if (idCuentaUsuario != null) {
            modelo.setCuentaUsuario(new CuentaUsuarioDAOImpl().leer(idCuentaUsuario));
        }

        mapearCamposPersona(rs, modelo);
        modelo.setCategoria(CategoriaCliente.valueOf(rs.getString("categoria")));
        modelo.setLineaCredito(rs.getDouble("lineaCredito"));
        modelo.setActivo(rs.getBoolean("activo"));
        return modelo;
    }

    private int setCamposCliente(PreparedStatement cmd, Cliente modelo) throws SQLException {
        setCuentaUsuarioNullable(cmd, 1, modelo);
        int idx = setCamposPersona(cmd, 2, modelo);
        cmd.setString(idx, modelo.getCategoria().name());
        cmd.setDouble(idx + 1, modelo.getLineaCredito());
        cmd.setBoolean(idx + 2, modelo.isActivo());
        return idx + 3;
    }
}
