package pe.edu.pucp.inf30.softprog.daoimpl.clientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import pe.edu.pucp.inf30.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.BaseDAO;
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
        
        String sql = 
                "INSERT INTO CLIENTE("
                + " idCuentaUsuario, "
                + " dni, "
                + " nombre, "
                + " apellidoPaterno, "
                + " genero, "
                + " fechaNacimiento, "
                + " categoria, "
                + " lineaCredito, "
                + " activo) "
                + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        PreparedStatement cmd = conn.prepareStatement(sql, 
                Statement.RETURN_GENERATED_KEYS);
        cmd.setInt(1, modelo.getCuentaUsuario().getId());
        cmd.setString(2, modelo.getDni());
        cmd.setString(3, modelo.getNombre());
        cmd.setString(4, modelo.getApellidoPaterno());
        cmd.setString(5, String.valueOf(modelo.getGenero()));
        cmd.setDate(6, new java.sql.Date(
                modelo.getFechaNacimiento().getTime()));
        cmd.setString(7, modelo.getCategoria().name());
        cmd.setDouble(8, modelo.getLineaCredito());
        cmd.setBoolean(9, modelo.isActivo());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            Cliente modelo) throws SQLException {
        
        String sql = 
                "UPDATE CLIENTE "
                + "SET"
                + " idCuentaUsuario = ?, "
                + " dni = ?, "
                + " nombre = ?, "
                + " apellidoPaterno = ?, "
                + " genero = ?, "
                + " fechaNacimiento = ?, "
                + " categoria = ?, "
                + " lineaCredito = ?, "
                + " activo = ?) "
                + "WHERE "
                + " id = ?";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setInt(1, modelo.getCuentaUsuario().getId());
        cmd.setString(2, modelo.getDni());
        cmd.setString(3, modelo.getNombre());
        cmd.setString(4, modelo.getApellidoPaterno());
        cmd.setString(5, String.valueOf(modelo.getGenero()));
        cmd.setDate(6, new java.sql.Date(modelo.getFechaNacimiento().getTime()));
        cmd.setString(7, modelo.getCategoria().name());
        cmd.setDouble(8, modelo.getLineaCredito());
        cmd.setBoolean(9, modelo.isActivo());
        cmd.setInt(10, modelo.getId());
        
        return cmd;
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, Integer id) 
            throws SQLException {
        
        String sql = 
                "DELETE "
                + "FROM CLIENTE "
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
                + " idCuentaUsuario, "
                + " dni, "
                + " nombr, "
                + " apellidoPaterno, "
                + " genero, "
                + " fechaNacimiento, "
                + " categoria, "
                + " lineaCredito, "
                + " activo) "
                + "FROM CLIENTE "
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
                + " idCuentaUsuario, "
                + " dni, "
                + " nombr, "
                + " apellidoPaterno, "
                + " genero, "
                + " fechaNacimiento, "
                + " categoria, "
                + " lineaCredito, "
                + " activo) "
                + "FROM CLIENTE";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        
        return cmd;
    }

    @Override
    protected Cliente mapearModelo(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidoPaterno(rs.getString("apellidoPaterno"));
        cliente.setGenero(Genero.valueOf(rs.getString("genero")));
        cliente.setFechaNacimiento(rs.getTimestamp("fechaNacimiento"));
        cliente.setCategoria(
                CategoriaCliente.valueOf(rs.getString("categoria")));
        cliente.setActivo(rs.getBoolean("activo"));
        
        return cliente;
    }

    protected PreparedStatement comandoBuscarPorDni(Connection conn, String dni) 
            throws SQLException {
        
        String sql = 
                "SELECT "
                + " id, "
                + " idCuentaUsuario, "
                + " dni, "
                + " nombr, "
                + " apellidoPaterno, "
                + " genero, "
                + " fechaNacimiento, "
                + " categoria, "
                + " lineaCredito, "
                + " activo) "
                + "FROM CLIENTE "
                + "WHERE dni = ?";
        
        PreparedStatement cmd = conn.prepareStatement(sql);
        cmd.setString(1, dni);
        
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
