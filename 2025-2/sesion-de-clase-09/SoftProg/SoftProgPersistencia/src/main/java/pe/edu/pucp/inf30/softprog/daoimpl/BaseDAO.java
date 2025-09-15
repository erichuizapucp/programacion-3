package pe.edu.pucp.inf30.softprog.daoimpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.dao.Persistible;
import pe.edu.pucp.inf30.softprog.db.DBFactoryProvider;
import pe.edu.pucp.inf30.softprog.db.DBManager;

/**
 *
 * @author eric
 */
public abstract class BaseDAO<T> implements Persistible<T, Integer> {    
    protected abstract PreparedStatement comandoCrear(Connection conn, 
            T modelo) throws SQLException;
    
    protected abstract PreparedStatement comandoActualizar(Connection conn, 
            T modelo) throws SQLException;
    
    protected abstract PreparedStatement comandoEliminar(Connection conn, 
            Integer id) throws SQLException;
    
    protected abstract PreparedStatement comandoLeer(Connection conn, 
            Integer id) throws SQLException;
    
    protected abstract PreparedStatement comandoLeerTodos(Connection conn) 
            throws SQLException;
    
    protected abstract T mapearModelo(ResultSet rs) throws SQLException;
    
    protected <R> R ejecutarComando(ComandoDAO<R> command) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection()) {
            return command.ejecutar(conn);
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
    public Integer crear(T modelo) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoCrear(conn, modelo)) {
                if (cmd.executeUpdate() == 0) {
                    return null;
                }
                
                if (cmd instanceof CallableStatement callableCmd) {
                    return callableCmd.getInt("p_id");
                }
                
                try (ResultSet rs = cmd.getGeneratedKeys()) {
                    if (rs.next()) {
                        return rs.getInt(1);
                    }
                }
                
                return null;
            }
        });
    }
    
    @Override
    public boolean actualizar(T modelo) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoActualizar(conn, modelo)) {
                return cmd.executeUpdate() > 0;
            }
        });
    }
    
    @Override
    public boolean eliminar(Integer id) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoEliminar(conn, id)) {
                return cmd.executeUpdate() > 0;
            }
        });
    }
    
    @Override
    public T leer(Integer id) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoLeer(conn, id)) {
                ResultSet rs = cmd.executeQuery();

                if (!rs.next()) {
                    System.err.println("No se encontro el registro con "
                            + "id: " + id);
                    return null;
                }

                return this.mapearModelo(rs);
            }
        });
    }
    
    @Override
    public List<T> leerTodos() {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoLeerTodos(conn)) {
                ResultSet rs = cmd.executeQuery();

                List<T> modelos = new ArrayList<>();
                while (rs.next()) {
                    modelos.add(this.mapearModelo(rs));
                }

                return modelos;
            }
        });
    }
}
