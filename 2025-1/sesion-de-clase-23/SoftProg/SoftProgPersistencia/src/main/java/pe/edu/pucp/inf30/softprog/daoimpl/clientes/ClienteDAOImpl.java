package pe.edu.pucp.inf30.softprog.daoimpl.clientes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.dao.clientes.IClienteDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAOImpl;
import pe.edu.pucp.inf30.softprog.model.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.model.clientes.Categoria;

/**
 *
 * @author eric
 */
public class ClienteDAOImpl extends BaseDAOImpl<Cliente> implements IClienteDAO {
    @Override
    protected CallableStatement comandoInsertar(Connection conn, Cliente modelo) throws SQLException {
//        String call = getPrefijoLlamada();
        String sql = "{CALL insertarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
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

    @Override
    protected CallableStatement comandoModificar(Connection conn, Cliente modelo) throws SQLException {
        String sql = "{CALL modificarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setNull("p_idCuentaUsuario", Types.INTEGER);
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

    @Override
    protected CallableStatement comandoEliminar(Connection conn, int id) throws SQLException {
        String sql = "{CALL eliminarCliente(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoBuscar(Connection conn, int id) throws SQLException {
        String sql = "{CALL buscarClientePorId(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt(1, id);
        return cmd;
    }

    @Override
    protected CallableStatement comandoListar(Connection conn) throws SQLException {
        String sql = "{CALL listarClientes()}";
        CallableStatement cmd = conn.prepareCall(sql);
        return cmd;
    }

    @Override
    protected Cliente mapearModelo(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidoPaterno(rs.getString("apellidoPaterno"));
        cliente.setGenero(rs.getString("genero").charAt(0));
        cliente.setFechaNacimiento(rs.getTimestamp("fechaNacimiento"));
        cliente.setCategoria(Categoria.valueOf(rs.getString("categoria")));
        cliente.setActivo(rs.getBoolean("activo"));
        return cliente;
    }

    protected CallableStatement comandoBuscarPorDni(Connection conn, String dni) throws SQLException {
        String sql = "{CALL buscarClientePorDni(?)}";
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_dni", dni);
        return cmd;
    }
    
    @Override
    public Cliente buscarPorDni(String dni) {
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
