package pe.edu.pucp.softprog.dao.impl.ventas;

import pe.edu.pucp.softprog.dao.impl.ProductoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.RegistroDAOImpl;
import pe.edu.pucp.softprog.dao.transacciones.TransactionsManager;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

class LineaOrdenVentaDAOImpl extends RegistroDAOImpl<LineaOrdenVenta>
        implements LineaOrdenVentaDAO {

    @Override
    public void insertLineas(int idOrden, List<LineaOrdenVenta> lineasOrdenVenta) throws SQLException {
        Connection connection = TransactionsManager.getConnection();

        String sql = "{call insertar_linea_orden_venta(?, ?, ?, ?, ?, ?)}";
        try (CallableStatement cmd = connection.prepareCall(sql)) {
            for (LineaOrdenVenta lineaOrdenVenta : lineasOrdenVenta) {
                cmd.setInt("p_id_orden_venta", idOrden);
                cmd.setInt("p_id_producto", lineaOrdenVenta.getProducto().getId());
                cmd.setInt("p_cantidad", lineaOrdenVenta.getCantidad());
                cmd.setDouble("p_sub_total", lineaOrdenVenta.getSubTotal());
                cmd.setBoolean("p_activo", true);
                cmd.registerOutParameter("p_id", Types.INTEGER);

                if (cmd.executeUpdate() == 0) {
                    throw new SQLException("No se pudo insertar la línea de la orden de venta");
                }

                lineaOrdenVenta.setId(cmd.getInt("p_id"));
            }
        }
    }

    @Override
    public void deleteLineas(int idOrden) throws SQLException {
        Connection connection = TransactionsManager.getConnection();

        String sql = "{call eliminar_lineas_por_orden_venta(?)}";
        try (CallableStatement cmd = connection.prepareCall(sql)) {
            cmd.setInt("p_id_orden_venta", idOrden);
            cmd.executeUpdate();
        }
    }

    @Override
    public List<LineaOrdenVenta> findByOrderId(int idOrden) throws SQLException {
        String sql = "{call listar_lineas_por_orden_venta(?)}";
        try (Connection connection = DBManager.getInstance().getConnection();
             CallableStatement cmd = connection.prepareCall(sql)) {

            cmd.setInt("p_id_orden_venta", idOrden);
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
