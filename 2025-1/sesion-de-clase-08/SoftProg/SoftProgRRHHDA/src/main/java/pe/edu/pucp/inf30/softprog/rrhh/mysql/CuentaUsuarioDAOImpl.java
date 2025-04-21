package pe.edu.pucp.inf30.softprog.rrhh.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.inf30.softprog.config.DBManager;
import pe.edu.pucp.inf30.softprog.rrhh.dao.ICuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.CuentaUsuario;

public class CuentaUsuarioDAOImpl implements ICuentaUsuarioDAO {

    @Override
    public int insertar(CuentaUsuario cuentaUsuario) {
        String sql = "INSERT CUENTA_USUARIO(userName, password, activo) VALUES(?, ?, ?)";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            ps.setString(1, cuentaUsuario.getUserName());
            ps.setString(2, cuentaUsuario.getPassword());
            ps.setBoolean(3, cuentaUsuario.isActivo());
            
            if (ps.executeUpdate() == 0) {
                System.err.println("El registro no se inserto.");
                return 0;
            }
            
            try (ResultSet rs = ps.getGeneratedKeys()) {
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
    public boolean modificar(CuentaUsuario cuentaUsuario) {
        String sql = "UPDATE CUENTA_USUARIO SET userName = ?, password = ?, activo = ? WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cs = conn.prepareStatement(sql)) {
            
            cs.setString(1, cuentaUsuario.getUserName());
            cs.setString(2, cuentaUsuario.getPassword());
            cs.setBoolean(3, cuentaUsuario.isActivo());
            cs.setInt(4, cuentaUsuario.getId());
            
            return cs.executeUpdate() > 0;
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
        String sql = "DELETE FROM CUENTA_USUARIO WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            
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
    public CuentaUsuario buscar(int id) {
        String sql = "SELECT * FROM CUENTA_USUARIO WHERE id = ?";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
            if (!rs.next()) {
                System.err.println("No se encontro la cuenta de usuario con id: " + id);
                return null;
            }
            
            CuentaUsuario cuentaUsuario = new CuentaUsuario();
            cuentaUsuario.setId(rs.getInt("id"));
            cuentaUsuario.setUserName(rs.getString("userName"));
            cuentaUsuario.setPassword(rs.getString("password"));
            cuentaUsuario.setActivo(rs.getBoolean("activo"));
            
            return cuentaUsuario;
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
    public List<CuentaUsuario> listar() {
        String sql = "SELECT * FROM CUENTA_USUARIO";
        try (Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            
            List<CuentaUsuario> cuentas = new ArrayList<>();
            while (rs.next()) {
                CuentaUsuario cuentaUsuario = new CuentaUsuario();
                cuentaUsuario.setId(rs.getInt("id"));
                cuentaUsuario.setUserName(rs.getString("userName"));
                cuentaUsuario.setPassword(rs.getString("password"));
                cuentaUsuario.setActivo(rs.getBoolean("activo"));
                cuentas.add(cuentaUsuario);
            }
            
            return cuentas;
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