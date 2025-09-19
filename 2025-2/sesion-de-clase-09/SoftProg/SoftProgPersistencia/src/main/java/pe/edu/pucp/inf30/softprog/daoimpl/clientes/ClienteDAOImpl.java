package pe.edu.pucp.inf30.softprog.daoimpl.clientes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import pe.edu.pucp.inf30.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Genero;
import pe.edu.pucp.inf30.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;

/**
 *
 * @author eric
 */
public class ClienteDAOImpl extends BaseDAO<Cliente> implements ClienteDAO {
    @Override
    protected PreparedStatement comandoCrear(Connection conn, Cliente modelo) 
            throws SQLException {
        
        String sql = "{call insertarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idCuentaUsuario", modelo.getCuentaUsuario().getId());
        cmd.setString("p_dni", modelo.getDni());
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_apellidoPaterno", modelo.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(modelo.getGenero()));
        cmd.setDate("p_fechaNacimiento", new java.sql.Date(
                modelo.getFechaNacimiento().getTime()));
        cmd.setString("p_categoria", modelo.getCategoria().name());
        cmd.setDouble("p_lineaCredito", modelo.getLineaCredito());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.registerOutParameter("p_id", Types.INTEGER);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            Cliente modelo) throws SQLException {
        
        String sql = "{call modificarCliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_idCuentaUsuario", modelo.getCuentaUsuario().getId());
        cmd.setString("p_dni", modelo.getDni());
        cmd.setString("p_nombre", modelo.getNombre());
        cmd.setString("p_apellidoPaterno", modelo.getApellidoPaterno());
        cmd.setString("p_genero", String.valueOf(modelo.getGenero()));
        cmd.setDate("p_fechaNacimiento", 
                new java.sql.Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString("p_categoria", modelo.getCategoria().name());
        cmd.setDouble("p_lineaCredito", modelo.getLineaCredito());
        cmd.setBoolean("p_activo", modelo.isActivo());
        cmd.setInt("p_id", modelo.getId());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) 
            throws SQLException {
        
        String sql = "{call eliminarCliente(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, Integer id) 
            throws SQLException {
        
        String sql = "{call buscarClientePorId(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setInt("p_id", id);
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException {
        
        String sql = "{call listarClientes()}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        
        return cmd;
    }

    @Override
    protected Cliente mapearModelo(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setCuentaUsuario(
                new CuentaUsuarioDAOImpl().leer(rs.getInt("idCuentaUsuario")));
        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidoPaterno(rs.getString("apellidoPaterno"));
        cliente.setGenero(Genero.valueOf(rs.getString("genero")));
        cliente.setFechaNacimiento(rs.getTimestamp("fechaNacimiento"));
        cliente.setLineaCredito(rs.getDouble("lineaCredito"));
        cliente.setCategoria(
                CategoriaCliente.valueOf(rs.getString("categoria")));
        cliente.setActivo(rs.getBoolean("activo"));
        
        return cliente;
    }

    protected PreparedStatement comandoBuscarPorDni(Connection conn, String dni) 
            throws SQLException {
        
        String sql = "{call buscarClientePorDni(?)}";
        
        CallableStatement cmd = conn.prepareCall(sql);
        cmd.setString("p_dni", dni);
        
        return cmd;
    }
    
    @Override
    public Cliente buscarPorDni(String dni) {
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
