package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.Persona;

import java.sql.ResultSet;
import java.sql.SQLException;


public abstract class PersonaDAOImpl<T extends Persona> extends RegistroDAOImpl<T> {
    @Override
    protected T mapear(ResultSet rs, T persona) throws SQLException {
        super.mapear(rs, persona);

        persona.setDni(rs.getString("dni"));
        persona.setNombre(rs.getString("nombre"));
        persona.setApellidoPaterno(rs.getString("apellido_paterno"));
        persona.setGenero(Enum.valueOf(Genero.class, rs.getString("genero")));
        persona.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());

        int idCuentaUsuario = rs.getInt("id_cuenta_usuario");
        if (!rs.wasNull()) {
            persona.setCuentaUsuario(new CuentaUsuarioDAOImpl().findById(idCuentaUsuario));
        }
        else {
            persona.setCuentaUsuario(null);
        }

        return persona;
    }
}
