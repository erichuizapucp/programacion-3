package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.ClienteDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl implements ClienteDAO {
    @Override
    public List<Cliente> findAll() throws SQLException {
        String sql =
                """
                SELECT
                    id,
                    id_cuenta_usuario,
                    dni,
                    nombre,
                    apellido_paterno,
                    genero,
                    fecha_nacimiento,
                    categoria,
                    linea_credito,
                    activo
                FROM cliente
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery()) {

            List<Cliente> clientes = new ArrayList<>();
            while (rs.next()) {
                clientes.add(mapear(rs, new Cliente()));
            }
            return clientes;
        }
    }

    @Override
    public Cliente findById(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql =
                """
                SELECT
                    id,
                    id_cuenta_usuario,
                    dni,
                    nombre,
                    apellido_paterno,
                    genero,
                    fecha_nacimiento,
                    categoria,
                    linea_credito,
                    activo
                FROM cliente WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Cliente()) : null;
            }
        }
    }

    @Override
    public void insert(Cliente cliente) throws SQLException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        String sql =
                """
                INSERT INTO cliente (
                  id_cuenta_usuario,
                  dni,
                  nombre,
                  apellido_paterno,
                  genero,
                  fecha_nacimiento,
                  categoria,
                  linea_credito,
                  activo)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            cmd.setInt(1, cliente.getCuentaUsuario().getId());
            cmd.setString(2, cliente.getDni());
            cmd.setString(3, cliente.getNombre());
            cmd.setString(4, cliente.getApellidoPaterno());
            cmd.setString(5, cliente.getGenero().name());
            cmd.setDate(6, Date.valueOf(cliente.getFechaNacimiento()));
            cmd.setString(7, cliente.getCategoria().name());
            cmd.setDouble(8, cliente.getLineaCredito());
            cmd.setBoolean(9, cliente.isActivo());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el cliente");
            }

            try (ResultSet rs = cmd.getGeneratedKeys()) {
                if (rs.next()) {
                    cliente.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        String sql =
                """
                UPDATE cliente
                SET
                    id_cuenta_usuario = ?,
                    dni = ?,
                    nombre = ?,
                    apellido_paterno = ?,
                    genero = ?,
                    fecha_nacimiento = ?,
                    categoria = ?,
                    linea_credito = ?,
                    activo = ?
                WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, cliente.getCuentaUsuario().getId());
            cmd.setString(2, cliente.getDni());
            cmd.setString(3, cliente.getNombre());
            cmd.setString(4, cliente.getApellidoPaterno());
            cmd.setString(5, cliente.getGenero().name());
            cmd.setDate(6, Date.valueOf(cliente.getFechaNacimiento()));
            cmd.setString(7, cliente.getCategoria().name());
            cmd.setDouble(8, cliente.getLineaCredito());
            cmd.setBoolean(9, cliente.isActivo());
            cmd.setInt(10, cliente.getId());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar el cliente");
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
                DELETE FROM cliente WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar el cliente");
            }
        }
    }

    private Cliente mapear(ResultSet rs, Cliente cliente) throws SQLException {
        cliente.setId(rs.getInt("id"));
        cliente.setActivo(rs.getBoolean("activo"));

        cliente.setDni(rs.getString("dni"));
        cliente.setNombre(rs.getString("nombre"));
        cliente.setApellidoPaterno(rs.getString("apellido_paterno"));
        cliente.setGenero(Enum.valueOf(Genero.class, rs.getString("genero")));
        cliente.setFechaNacimiento(rs.getDate("fecha_nacimiento").toLocalDate());

        int idCuentaUsuario = rs.getInt("id_cuenta_usuario");
        if (!rs.wasNull()) {
            cliente.setCuentaUsuario(new CuentaUsuarioDAOImpl().findById(idCuentaUsuario));
        }
        else {
            cliente.setCuentaUsuario(null);
        }

        cliente.setCategoria(
                Enum.valueOf(CategoriaCliente.class, rs.getString("categoria")));
        cliente.setLineaCredito(rs.getDouble("linea_credito"));
        return cliente;
    }
}
