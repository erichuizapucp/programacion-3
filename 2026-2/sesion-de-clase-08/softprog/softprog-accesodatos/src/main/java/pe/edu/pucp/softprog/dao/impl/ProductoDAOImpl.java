package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.ProductoDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl extends RegistroDAOImpl<Producto> implements ProductoDAO {
    @Override
    public List<Producto> findAll() throws SQLException {
        String sql =
                """
                SELECT id, nombre, unidad_medida, precio, activo FROM producto
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql);
            ResultSet rs = cmd.executeQuery()) {

            List<Producto> productos = new ArrayList<>();
            while (rs.next()) {
                productos.add(mapear(rs, new Producto()));
            }
            return productos;
        }
    }

    @Override
    public Producto findById(Integer id) throws SQLException {
        if (id == null) {
            throw new IllegalArgumentException("El id no puede ser nulo");
        }

        String sql =
                """
                SELECT id, nombre, unidad_medida, precio, activo FROM producto WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Producto()) : null;
            }
        }
    }

    @Override
    public Producto findByName(String nombre) throws SQLException {
        if (nombre == null) {
            throw new IllegalArgumentException("El nombre no puede ser nulo");
        }

        String sql =
                """
                SELECT id, nombre, unidad_medida, precio, activo FROM producto WHERE nombre = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setString(1, nombre);

            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new Producto()) : null;
            }
        }
    }

    @Override
    public void insert(Producto producto) throws SQLException {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        String sql =
                """
                INSERT INTO producto (nombre, unidad_medida, precio, activo) VALUES (?, ?, ?, ?)
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql,
                    PreparedStatement.RETURN_GENERATED_KEYS)) {

            cmd.setString(1, producto.getNombre());
            cmd.setString(2, producto.getUnidadMedida().name());
            cmd.setDouble(3, producto.getPrecio());
            cmd.setBoolean(4, producto.isActivo());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el producto");
            }

            try (ResultSet rs = cmd.getGeneratedKeys()) {
                if (rs.next()) {
                    producto.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public void update(Producto producto) throws SQLException {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        String sql =
                """
                UPDATE producto
                SET nombre = ?, unidad_medida = ?, precio = ?, activo = ?
                WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setString(1, producto.getNombre());
            cmd.setString(2, producto.getUnidadMedida().name());
            cmd.setDouble(3, producto.getPrecio());
            cmd.setBoolean(4, producto.isActivo());
            cmd.setInt(5, producto.getId());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar el producto");
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
                DELETE FROM producto WHERE id = ?
                """;
        try (
            Connection conn = DBManager.getInstance().getConnection();
            PreparedStatement cmd = conn.prepareStatement(sql)) {

            cmd.setInt(1, id);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar el producto");
            }
        }
    }

    @Override
    protected Producto mapear(ResultSet rs, Producto producto) throws SQLException {
        super.mapear(rs, producto);
        producto.setNombre(rs.getString("nombre"));
        producto.setUnidadMedida(
                Enum.valueOf(UnidadMedida.class, rs.getString("unidad_medida")));
        producto.setPrecio(rs.getDouble("precio"));
        return producto;
    }
}
