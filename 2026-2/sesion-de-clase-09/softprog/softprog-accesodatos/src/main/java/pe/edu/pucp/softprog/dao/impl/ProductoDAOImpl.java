package pe.edu.pucp.softprog.dao.impl;

import pe.edu.pucp.softprog.dao.ProductoDAO;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOImpl extends RegistroDAOImpl<Producto> implements ProductoDAO {
    @Override
    public List<Producto> findAll() throws SQLException {
        String sql = "{call listar_productos()}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql);
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

        String sql = "{call buscar_producto_por_id(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

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

        String sql = "{call buscar_producto_por_nombre(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_nombre", nombre);

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

        String sql = "{call insertar_producto(?, ?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_nombre", producto.getNombre());
            cmd.setString("p_unidad_medida", producto.getUnidadMedida().name());
            cmd.setDouble("p_precio", producto.getPrecio());
            cmd.setBoolean("p_activo", producto.isActivo());
            cmd.registerOutParameter("p_id", Types.INTEGER);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar el producto");
            }

            producto.setId(cmd.getInt("p_id"));
        }
    }

    @Override
    public void update(Producto producto) throws SQLException {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }

        String sql = "{call modificar_producto(?, ?, ?, ?, ?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setString("p_nombre", producto.getNombre());
            cmd.setString("p_unidad_medida", producto.getUnidadMedida().name());
            cmd.setDouble("p_precio", producto.getPrecio());
            cmd.setBoolean("p_activo", producto.isActivo());
            cmd.setInt("p_id", producto.getId());

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

        String sql = "{call eliminar_producto(?)}";
        try (
            Connection conn = DBManager.getInstance().getConnection();
            CallableStatement cmd = conn.prepareCall(sql)) {

            cmd.setInt("p_id", id);

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
