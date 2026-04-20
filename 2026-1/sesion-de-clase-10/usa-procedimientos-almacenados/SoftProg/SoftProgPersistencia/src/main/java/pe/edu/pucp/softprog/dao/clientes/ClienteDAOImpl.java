package pe.edu.pucp.softprog.dao.clientes;

import pe.edu.pucp.softprog.dao.PersonaBaseDAO;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class ClienteDAOImpl extends PersonaBaseDAO<Cliente> implements ClienteDAO {

    protected PreparedStatement comandoCrear(Connection conn,
                                             Cliente modelo) throws SQLException {
        String sql = "{call insertarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
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
        cmd.setString("p_categoria", modelo.getCategoria().name());
        cmd.setDouble("p_lineaCredito", modelo.getLineaCredito());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        return cmd;
    }

    protected PreparedStatement comandoActualizar(Connection conn,
                                                  Cliente modelo) throws SQLException {
        String sql = "{call modificarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
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
        cmd.setString("p_categoria", modelo.getCategoria().name());
        cmd.setDouble("p_lineaCredito", modelo.getLineaCredito());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        return cmd;
    }

    protected PreparedStatement comandoEliminar(Connection conn,
                                                Integer id) throws SQLException {
        String sql = "{call eliminarCliente(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    protected PreparedStatement comandoLeer(Connection conn,
                                            Integer id) throws SQLException {
        String sql = "{call buscarClientePorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        return cmd;
    }

    protected PreparedStatement comandoLeerTodos(Connection conn) throws SQLException {
        String sql = "{call listarClientes()}";
        return conn.prepareCall(sql);
    }

    @Override
    protected PreparedStatement comandoBuscarPorDni(Connection conn,
                                                    String dni) throws SQLException {
        String sql = "{call buscarClientePorDni(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_dni", dni);
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

}
