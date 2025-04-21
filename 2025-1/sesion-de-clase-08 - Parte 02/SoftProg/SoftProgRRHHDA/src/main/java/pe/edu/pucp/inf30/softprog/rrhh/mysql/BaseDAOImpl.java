package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.rrhh.dao.ICrud;

public abstract class BaseDAOImpl<T> implements ICrud<T> {
    protected abstract PreparedStatement comandoInsertar(Connection conn, T modelo) throws SQLException;
    protected abstract PreparedStatement comandoModificar(Connection conn, T modelo) throws SQLException;
    protected abstract PreparedStatement comandoEliminar(Connection conn, int id) throws SQLException;
    protected abstract PreparedStatement comandoBuscar(Connection conn, int id) throws SQLException;
    protected abstract PreparedStatement comandoListar(Connection conn) throws SQLException;
    
    protected abstract T mapearModelo(ResultSet rs) throws SQLException;
    
    @Override
    public int insertar(T modelo) {
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = this.comandoInsertar(conn, modelo);
        ) {
            if (cmd.executeUpdate() == 0) {
                System.err.println("El registro no se inserto.");
                return 0;
            }
            
            try (ResultSet rs = cmd.getGeneratedKeys()) {
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
    public boolean modificar(T modelo) {
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = this.comandoModificar(conn, modelo);
        ) {
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
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = this.comandoEliminar(conn, id);
        ) {
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
    public T buscar(int id) {
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = this.comandoBuscar(conn, id);
        ) {
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.err.println("No se encontro el registro con id: " + id);
                return null;
            }
            
            return this.mapearModelo(rs);
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
    public List<T> listar() {
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = this.comandoListar(conn);
        ) {
            ResultSet rs = ps.executeQuery();
            
            List<T> modelos = new ArrayList<>();
            while (rs.next()) {
                modelos.add(this.mapearModelo(rs));
            }
            
            return modelos;
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
