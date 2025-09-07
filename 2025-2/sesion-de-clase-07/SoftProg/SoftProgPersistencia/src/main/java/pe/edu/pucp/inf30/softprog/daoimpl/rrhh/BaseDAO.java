package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

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
abstract class BaseDAO<T> implements Persistible<T, Integer> {    
    protected abstract PreparedStatement comandoCrear(
            Connection conn, 
            T modelo) throws SQLException;
    
    protected abstract PreparedStatement comandoActualizar(
            Connection conn, 
            T modelo) throws SQLException;
    
    protected abstract PreparedStatement comandoEliminar(
            Connection conn, 
            Integer id) throws SQLException;
    
    protected abstract PreparedStatement comandoLeer(
            Connection conn, 
            Integer id) throws SQLException;
    
    protected abstract PreparedStatement comandoLeerTodos(
            Connection conn) throws SQLException;
    
    protected abstract T mapearModelo(ResultSet rs) throws SQLException;
    
    @Override
    public Integer crear(T modelo) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoCrear(conn, modelo)) {
            
            if (cmd.executeUpdate() == 0) {
                System.err.println("El registro no se inserto.");
                return null;
            }
            
            try (ResultSet rs = cmd.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : null;
            }
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la insercion: " + 
                    e.getMessage());
            throw new RuntimeException("No se pudo insertar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al insertar el "
                    + "registro.", e);
        }
    }
    
    @Override
    public boolean actualizar(T modelo) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection();
             PreparedStatement cmd = this.comandoActualizar(conn, modelo)) {
            return cmd.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la modificacion: " + 
                    e.getMessage());
            throw new RuntimeException("No se pudo modificar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al modificar el "
                    + "registro.", e);
        }
    }
    
    @Override
    public boolean eliminar(Integer id) {
        try (Connection conn = DBFactoryProvider.getManager().getConnection();
             PreparedStatement cmd = this.comandoEliminar(conn, id)) {
            return cmd.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la eliminacion: " + 
                    e.getMessage());
            throw new RuntimeException("No se pudo eliminar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al eliminar el "
                    + "registro.", e);
        }
    }
    
    @Override
    public T leer(Integer id) {
        try (Connection conn = DBFactoryProvider.getManager().getConnection();
             PreparedStatement cmd = this.comandoLeer(conn, id)) {
            ResultSet rs = cmd.executeQuery();
            
            if (!rs.next()) {
                System.err.println("No se encontro el registro con id: " + id);
                return null;
            }
            
            return this.mapearModelo(rs);
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante la busqueda: " + 
                    e.getMessage());
            throw new RuntimeException("No se pudo buscar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al buscar el "
                    + "registro.", e);
        }
    }
    
    @Override
    public List<T> leerTodos() {
        try (Connection conn = DBFactoryProvider.getManager().getConnection();
             PreparedStatement cmd = this.comandoLeerTodos(conn)) {
            ResultSet rs = cmd.executeQuery();
            
            List<T> modelos = new ArrayList<>();
            while (rs.next()) {
                modelos.add(this.mapearModelo(rs));
            }
            
            return modelos;
        }
        catch (SQLException e) {
            System.err.println("Error SQL durante el listado: " + 
                    e.getMessage());
            throw new RuntimeException("No se pudo listar el registro.", e);
        }
        catch (Exception e) {
            System.err.println("Error inpesperado: " + e.getMessage());
            throw new RuntimeException("Error inesperado al listar los "
                    + "registros.", e);
        }
    }
}
