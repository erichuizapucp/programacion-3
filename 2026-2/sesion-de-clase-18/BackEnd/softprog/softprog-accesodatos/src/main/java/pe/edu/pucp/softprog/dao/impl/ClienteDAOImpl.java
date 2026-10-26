package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.ClienteDAO;
import pe.edu.pucp.softprog.dao.transacciones.TransactionsManager;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOImpl extends PersonaDAOImpl<Cliente> implements ClienteDAO {
    @Override
    public List<Cliente> findAll() throws SQLException {
        String sql = "{call listar_clientes()}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql);
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

        String sql = "{call buscar_cliente_por_id(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Cliente()) : null;
            }
        }
    }

    @Override
    public Cliente findByDni(String dni) throws SQLException {
        if (dni == null) {
            throw new IllegalArgumentException("El dni no puede ser nulo");
        }

        String sql = "{call buscar_cliente_por_dni(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_dni", dni);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Cliente()) : null;
            }
        }
    }

    @Override
    public List<Cliente> filterByName(String nombre) throws SQLException {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }

        String sql = "{call filtrar_clientes_por_nombre(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_nombre", nombre);

            try (ResultSet rs = cmd.executeQuery()) {
                List<Cliente> clientes = new ArrayList<>();
                while (rs.next()) {
                    clientes.add(mapear(rs, new Cliente()));
                }
                return clientes;
            }
        }
    }

    @Override
    public Cliente findByCuenta(String userName) throws SQLException {
        if (userName == null) {
            throw new IllegalArgumentException("El user_name no puede ser nulo");
        }

        String sql = "{call buscar_cliente_por_cuenta(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_cuenta", userName);

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

        String sql = "{call insertar_cliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            if (cliente.getCuentaUsuario() != null) {
                cmd.setInt("p_id_cuenta_usuario", cliente.getCuentaUsuario().getId());
            } else {
                cmd.setNull("p_id_cuenta_usuario", Types.INTEGER);
            }
            cmd.setString("p_dni", cliente.getDni());
            cmd.setString("p_nombre", cliente.getNombre());
            cmd.setString("p_apellido_paterno", cliente.getApellidoPaterno());
            cmd.setString("p_genero", cliente.getGenero().name());
            cmd.setDate("p_fecha_nacimiento", Date.valueOf(cliente.getFechaNacimiento()));
            cmd.setString("p_categoria", cliente.getCategoria().name());
            cmd.setDouble("p_linea_credito", cliente.getLineaCredito());
            cmd.setBoolean("p_activo", cliente.isActivo());
            cmd.registerOutParameter("p_id", Types.INTEGER);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el cliente");
            }

            cliente.setId(cmd.getInt("p_id"));
        }
    }

    @Override
    public void update(Cliente cliente) throws SQLException {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }

        String sql = "{call modificar_cliente(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            if (cliente.getCuentaUsuario() != null) {
                cmd.setInt("p_id_cuenta_usuario", cliente.getCuentaUsuario().getId());
            } else {
                cmd.setNull("p_id_cuenta_usuario", Types.INTEGER);
            }
            cmd.setString("p_dni", cliente.getDni());
            cmd.setString("p_nombre", cliente.getNombre());
            cmd.setString("p_apellido_paterno", cliente.getApellidoPaterno());
            cmd.setString("p_genero", cliente.getGenero().name());
            cmd.setDate("p_fecha_nacimiento", Date.valueOf(cliente.getFechaNacimiento()));
            cmd.setString("p_categoria", cliente.getCategoria().name());
            cmd.setDouble("p_linea_credito", cliente.getLineaCredito());
            cmd.setBoolean("p_activo", cliente.isActivo());
            cmd.setInt("p_id", cliente.getId());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar el cliente");
            }
        }
    }

    @Override
    public void updateLineaCredito(int id, double lineaCredito) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = "{call modificar_linea_credito_cliente(?, ?)}";
        try (CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setDouble("p_linea_credito", lineaCredito);
            cmd.setInt("p_id", id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar la línea de crédito del cliente");
            }
        }
    }

    @Override
    public void delete(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql = "{call eliminar_cliente(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar el cliente");
            }
        }
    }

    @Override
    protected Cliente mapear(ResultSet rs, Cliente cliente) throws SQLException {
        super.mapear(rs, cliente);
        cliente.setCategoria(
                Enum.valueOf(CategoriaCliente.class, rs.getString("categoria")));
        cliente.setLineaCredito(rs.getDouble("linea_credito"));
        return cliente;
    }
}
