package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.sql.SQLException;


public interface CuentaUsuarioDAO extends DAO<CuentaUsuario, Integer> {
    CuentaUsuario findByUserName(String userName) throws SQLException;
}
