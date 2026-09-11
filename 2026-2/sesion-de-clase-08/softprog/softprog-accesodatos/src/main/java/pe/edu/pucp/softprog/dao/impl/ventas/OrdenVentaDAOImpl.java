package pe.edu.pucp.softprog.dao.impl.ventas;

import pe.edu.pucp.softprog.dao.OrdenVentaDAO;
import pe.edu.pucp.softprog.dao.impl.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.impl.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.RegistroDAOImpl;
import pe.edu.pucp.softprog.dao.transacciones.TransactionsManager;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenVentaDAOImpl extends RegistroDAOImpl<OrdenVenta> implements OrdenVentaDAO {
    @Override
    public List<OrdenVenta> findAll() throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = """
                SELECT
                    id, id_cliente, id_empleado, total, activo
                FROM orden_venta
                """;

        try (PreparedStatement cmd = conn.prepareStatement(sql);
             ResultSet rs = cmd.executeQuery()) {

            List<OrdenVenta> ordenes = new ArrayList<>();
            while (rs.next()) {
                ordenes.add(mapear(rs, new OrdenVenta()));
            }
            return ordenes;
        }
    }

    @Override
    public OrdenVenta findById(Integer integer) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = """
                SELECT
                    id, id_cliente, id_empleado, total, activo
                FROM orden_venta
                WHERE id = ?
                """;

        try (PreparedStatement cmd = conn.prepareStatement(sql)) {
            cmd.setInt(1, integer);
            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new OrdenVenta()) : null;
            }
        }
    }

    @Override
    public void insert(OrdenVenta ordenVenta) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = """
                INSERT INTO orden_venta (id_cliente, id_empleado, total, activo)
                VALUES (?, ?, ?, ?)
                """;

        try (PreparedStatement cmd = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            if (ordenVenta.getCliente() != null) {
                cmd.setInt(1, ordenVenta.getCliente().getId());
            } else {
                cmd.setNull(1, java.sql.Types.INTEGER);
            }

            if (ordenVenta.getEmpleado() != null) {
                cmd.setInt(2, ordenVenta.getEmpleado().getId());
            } else {
                cmd.setNull(2, java.sql.Types.INTEGER);
            }

            cmd.setDouble(3, ordenVenta.getTotal());
            cmd.setBoolean(4, ordenVenta.isActivo());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar la orden de venta");
            }

            try (ResultSet generatedKeys = cmd.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    ordenVenta.setId(generatedKeys.getInt(1));
                }
            }

            LineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.insertLineas(ordenVenta.getId(), ordenVenta.getLineas());
        }
    }

    @Override
    public void update(OrdenVenta modelo) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = """
                UPDATE orden_venta
                SET id_cliente = ?, id_empleado = ?, total = ?, activo = ?
                WHERE id = ?
                """;

        try (PreparedStatement cmd = conn.prepareStatement(sql)) {
            if (modelo.getCliente() != null) {
                cmd.setInt(1, modelo.getCliente().getId());
            } else {
                cmd.setNull(1, java.sql.Types.INTEGER);
            }

            if (modelo.getEmpleado() != null) {
                cmd.setInt(2, modelo.getEmpleado().getId());
            } else {
                cmd.setNull(2, java.sql.Types.INTEGER);
            }

            cmd.setDouble(3, modelo.getTotal());
            cmd.setBoolean(4, modelo.isActivo());
            cmd.setInt(5, modelo.getId());

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo actualizar la orden de venta");
            }

            LineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.deleteLineas(modelo.getId());
            lineaOrdenVentaDAO.insertLineas(modelo.getId(), modelo.getLineas());
        }
    }

    @Override
    public void delete(Integer integer) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = """
                DELETE FROM orden_venta WHERE id = ?
                """;

        LineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
        lineaOrdenVentaDAO.deleteLineas(integer);

        try (PreparedStatement cmd = conn.prepareStatement(sql)) {
            cmd.setInt(1, integer);
            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo eliminar la orden de venta");
            }
        }
    }

    @Override
    protected OrdenVenta mapear(ResultSet rs, OrdenVenta orden) throws SQLException {
        super.mapear(rs, orden);

        orden.setId(rs.getInt("id"));
        mapearCliente(rs, orden);
        mapearEmpleado(rs, orden);
        orden.setTotal(rs.getDouble("total"));

        mapearLineas(rs, orden);

        return orden;
    }

    private void mapearCliente(ResultSet rs, OrdenVenta orden) throws SQLException {
        int idCliente = rs.getInt("id_cliente");
        if (!rs.wasNull()) {
            orden.setCliente(new ClienteDAOImpl().findById(idCliente));
        }
        else {
            orden.setCliente(null);
        }
    }

    private void mapearEmpleado(ResultSet rs, OrdenVenta orden) throws SQLException {
        int idEmpleado = rs.getInt("id_empleado");
        if (!rs.wasNull()) {
            orden.setEmpleado(new EmpleadoDAOImpl().findById(idEmpleado));
        }
        else {
            orden.setEmpleado(null);
        }
    }

    private void mapearLineas(ResultSet rs, OrdenVenta orden) throws SQLException {
        LineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
        List<LineaOrdenVenta> lineas = lineaOrdenVentaDAO.findByOrderId(rs.getInt("id"));
        orden.setLineas(lineas);
    }
}
