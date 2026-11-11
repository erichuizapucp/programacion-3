package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.CuentaUsuarioDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class CuentaUsuarioDAOImpl extends RegistroDAOImpl<CuentaUsuario> implements CuentaUsuarioDAO {
    @Override
    public List<CuentaUsuario> findAll() throws SQLException {
        String sql =
                """
                SELECT id, user_name, password, activo
                FROM cuenta_usuario
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery()) {

            List<CuentaUsuario> cuentas = new ArrayList<>();
            while (rs.next()) {
                cuentas.add(mapear(rs, new CuentaUsuario()));
            }
            return cuentas;
        }
    }

    @Override
    public CuentaUsuario findById(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql =
                """
                SELECT id, user_name, password, activo
                FROM cuenta_usuario
                WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new CuentaUsuario()) : null;
            }
        }
    }

    @Override
    public CuentaUsuario findByUserName(String userName) throws SQLException {
        if (userName == null) {
            throw new IllegalArgumentException("El user_name no puede ser nulo");
        }

        String sql =
                """
                SELECT id, user_name, password, activo
                FROM cuenta_usuario
                WHERE user_name = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setString(1, userName);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new CuentaUsuario()) : null;
            }
        }
    }

    @Override
    public void insert(CuentaUsuario cuentaUsuario) throws SQLException {
        if (cuentaUsuario == null) {
            throw new IllegalArgumentException("La cuenta de usuario no puede ser nula");
        }

        String sql =
                """
                INSERT INTO
                    cuenta_usuario (user_name, password, activo)
                VALUES (?, ?, ?)
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            cmd.setString(1, cuentaUsuario.getUserName());
            cmd.setString(2, cuentaUsuario.getPassword());
            cmd.setBoolean(3, cuentaUsuario.isActivo());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar la cuenta de usuario");
            }

            try (ResultSet rs = cmd.getGeneratedKeys()) {
                if (rs.next()) {
                    cuentaUsuario.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(CuentaUsuario cuentaUsuario) throws SQLException {
        if (cuentaUsuario == null) {
            throw new IllegalArgumentException("La cuenta de usuario no puede ser nula");
        }

        String sql =
                """
                UPDATE cuenta_usuario
                SET user_name = ?, password = ?, activo = ?
                WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setString(1, cuentaUsuario.getUserName());
            cmd.setString(2, cuentaUsuario.getPassword());
            cmd.setBoolean(3, cuentaUsuario.isActivo());
            cmd.setInt(4, cuentaUsuario.getId());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar la cuenta de usuario");
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql =
                """
                DELETE FROM cuenta_usuario WHERE id = ?
                """;
        try (
                Connection conn = DBManager.getInstance().getConnection();
                PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar la cuenta de usuario");
            }
        }
    }

    @Override
    protected CuentaUsuario mapear(ResultSet rs, CuentaUsuario cuenta) throws SQLException {
        super.mapear(rs, cuenta);
        cuenta.setUserName(rs.getString("user_name"));
        cuenta.setPassword(rs.getString("password"));
        return cuenta;
    }
}
