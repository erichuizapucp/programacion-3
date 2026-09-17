package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.CuentaUsuarioDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;


public class CuentaUsuarioDAOImpl extends RegistroDAOImpl<CuentaUsuario> implements CuentaUsuarioDAO {
    @Override
    public List<CuentaUsuario> findAll() throws SQLException {
        String sql = "{call listar_cuenta_usuarios()}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql);
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

        String sql = "{call buscar_cuenta_usuario_por_id(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

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

        String sql = "{call buscar_cuenta_usuario_por_user_name(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_user_name", userName);

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

        String sql = "{call insertar_cuenta_usuario(?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_user_name", cuentaUsuario.getUserName());
            cmd.setString("p_password", cuentaUsuario.getPassword());
            cmd.setBoolean("p_activo", cuentaUsuario.isActivo());
            cmd.registerOutParameter("p_id", Types.INTEGER);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar la cuenta de usuario");
            }

            cuentaUsuario.setId(cmd.getInt("p_id"));
        }
    }

    @Override
    public void update(CuentaUsuario cuentaUsuario) throws SQLException {
        if (cuentaUsuario == null) {
            throw new IllegalArgumentException("La cuenta de usuario no puede ser nula");
        }

        String sql = "{call modificar_cuenta_usuario(?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_user_name", cuentaUsuario.getUserName());
            cmd.setString("p_password", cuentaUsuario.getPassword());
            cmd.setBoolean("p_activo", cuentaUsuario.isActivo());
            cmd.setInt("p_id", cuentaUsuario.getId());

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

        String sql = "{call eliminar_cuenta_usuario(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

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
