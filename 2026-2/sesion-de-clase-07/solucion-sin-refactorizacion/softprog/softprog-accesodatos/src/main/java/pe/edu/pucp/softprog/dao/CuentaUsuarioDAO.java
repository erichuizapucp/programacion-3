package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.sql.SQLException;
import java.util.List;


public interface CuentaUsuarioDAO {
    List<CuentaUsuario> findAll() throws SQLException;
    CuentaUsuario findById(Integer id) throws SQLException;
    void insert(CuentaUsuario cuentaUsuario) throws SQLException;
    void update(CuentaUsuario cuentaUsuario) throws SQLException;
    void delete(Integer id) throws SQLException;
}
