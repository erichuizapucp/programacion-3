package pe.edu.pucp.softprog.dao.impl.ventas;

import pe.edu.pucp.softprog.dao.impl.ProductoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.RegistroDAOImpl;
import pe.edu.pucp.softprog.dao.transacciones.TransactionsManager;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class LineaOrdenVentaDAOImpl extends RegistroDAOImpl<LineaOrdenVenta> implements LineaOrdenVentaDAO {
    @Override
    public void insertLineas(int idOrden, List<LineaOrdenVenta> lineasOrdenVenta) throws SQLException {
        Connection connection = TransactionsManager.getConnection();

        String sql =
                """
                INSERT INTO linea_orden_venta (
                    id_orden_venta,
                    id_producto,
                    cantidad,
                    sub_total)
                VALUES (?, ?, ?, ?)
                """;
        try (PreparedStatement cmd = connection.prepareStatement(sql)) {
            for (LineaOrdenVenta lineaOrdenVenta : lineasOrdenVenta) {
                cmd.setInt(1, idOrden);
                cmd.setInt(2, lineaOrdenVenta.getProducto().getId());
                cmd.setInt(3, lineaOrdenVenta.getCantidad());
                cmd.setDouble(4, lineaOrdenVenta.getSubTotal());
                cmd.addBatch();
            }
            cmd.executeBatch();
        }
    }

    @Override
    public void deleteLineas(int idOrden) throws SQLException {
        Connection connection = TransactionsManager.getConnection();

        String sql =
                """
                DELETE FROM linea_orden_venta WHERE id_orden_venta = ?
                """;
        try (PreparedStatement cmd = connection.prepareStatement(sql)) {
            cmd.setInt(1, idOrden);
            cmd.executeUpdate();
        }
    }

    @Override
    public List<LineaOrdenVenta> findByOrderId(int idOrden) throws SQLException {
        Connection connection = TransactionsManager.getConnection();

        String sql =
                """
                SELECT id, id_orden_venta, id_producto, cantidad, sub_total, activo
                FROM linea_orden_venta
                WHERE id_orden_venta = ?
                """;
        try (PreparedStatement cmd = connection.prepareStatement(sql)) {
            cmd.setInt(1, idOrden);
            try (ResultSet rs = cmd.executeQuery()) {
                List<LineaOrdenVenta> lineasOrdenVenta = new ArrayList<>();
                while (rs.next()) {
                    LineaOrdenVenta lineaOrdenVenta = mapear(rs, new LineaOrdenVenta());
                    lineasOrdenVenta.add(lineaOrdenVenta);
                }
                return lineasOrdenVenta;
            }
        }
    }

    @Override
    protected LineaOrdenVenta mapear(ResultSet rs, LineaOrdenVenta lineaOrdenVenta) throws SQLException {
        super.mapear(rs, lineaOrdenVenta);

        lineaOrdenVenta.setProducto(
                new ProductoDAOImpl().findById(rs.getInt("id_producto")));
        lineaOrdenVenta.setCantidad(rs.getInt("cantidad"));
        lineaOrdenVenta.setSubTotal(rs.getDouble("sub_total"));

        return lineaOrdenVenta;
    }
}
