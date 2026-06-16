package pe.edu.pucp.softprog.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.Persona;

public abstract class PersonaBaseDAO<M extends Persona> extends DefaultBaseDAO<M> {

    protected abstract PreparedStatement comandoBuscarPorDni(Connection conn,
                                                             String dni) throws SQLException;

    public M buscarPorDni(String dni) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = comandoBuscarPorDni(conn, dni);
                 ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapearModelo(rs) : null;
            }
        });
    }

    protected int setCamposPersona(PreparedStatement cmd, int startIndex,
                                   M modelo) throws SQLException {
        cmd.setString(startIndex, modelo.getDni());
        cmd.setString(startIndex + 1, modelo.getNombre());
        cmd.setString(startIndex + 2, modelo.getApellidoPaterno());
        cmd.setString(startIndex + 3, modelo.getGenero().name());
        cmd.setDate(startIndex + 4, new Date(modelo.getFechaNacimiento().getTime()));
        return startIndex + 5;
    }

    protected void mapearCamposPersona(ResultSet rs, M modelo) throws SQLException {
        modelo.setDni(rs.getString("dni"));
        modelo.setNombre(rs.getString("nombre"));
        modelo.setApellidoPaterno(rs.getString("apellidoPaterno"));
        modelo.setGenero(Genero.valueOf(rs.getString("genero")));
        modelo.setFechaNacimiento(rs.getDate("fechaNacimiento"));
    }

    protected Integer getIdCuentaUsuario(M modelo) {
        if (modelo.getCuentaUsuario() == null) {
            return null;
        }
        return modelo.getCuentaUsuario().getId();
    }

    protected void setCuentaUsuarioNullable(PreparedStatement cmd, int index,
                                            M modelo) throws SQLException {
        setEnteroNullable(cmd, index, getIdCuentaUsuario(modelo));
    }

    protected Integer leerIdCuentaUsuario(ResultSet rs) throws SQLException {
        return leerEnteroNullable(rs, "idCuentaUsuario");
    }

}

