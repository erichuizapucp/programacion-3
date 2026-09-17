package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.modelo.Registro;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class RegistroDAOImpl<T extends Registro> {
    protected T mapear(ResultSet rs, T registro) throws SQLException {
        registro.setId(rs.getInt("id"));
        registro.setActivo(rs.getBoolean("activo"));
        return registro;
    }
}
