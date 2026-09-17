package pe.edu.pucp.softprog.dao.impl.ventas;

import pe.edu.pucp.softprog.dao.OrdenVentaDAO;
import pe.edu.pucp.softprog.dao.impl.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.impl.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.RegistroDAOImpl;
import pe.edu.pucp.softprog.dao.transacciones.TransactionsManager;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class OrdenVentaDAOImpl extends RegistroDAOImpl<OrdenVenta> implements OrdenVentaDAO {
    @Override
    public List<OrdenVenta> findAll() throws SQLException {
        String sql = "{call listar_ordenes_venta()}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql);
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
        String sql = "{call buscar_orden_venta_por_id(?)}";

        try (Connection conn = DBManager.getInstance().getConnection();
             CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id", integer);
            try (ResultSet rs = cmd.executeQuery()) {
                return rs.next() ? mapear(rs, new OrdenVenta()) : null;
            }
        }
    }

    @Override
    public void insert(OrdenVenta ordenVenta) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = "{call insertar_orden_venta(?, ?, ?, ?, ?)}";

        try (CallableStatement cmd = conn.prepareCall(sql)) {
            if (ordenVenta.getCliente() != null) {
                cmd.setInt("p_id_cliente", ordenVenta.getCliente().getId());
            } else {
                cmd.setNull("p_id_cliente", Types.INTEGER);
            }

            if (ordenVenta.getEmpleado() != null) {
                cmd.setInt("p_id_empleado", ordenVenta.getEmpleado().getId());
            } else {
                cmd.setNull("p_id_empleado", Types.INTEGER);
            }

            cmd.setDouble("p_total", ordenVenta.getTotal());
            cmd.setBoolean("p_activo", ordenVenta.isActivo());
            cmd.registerOutParameter("p_id", Types.INTEGER);

            if (cmd.executeUpdate() == 0) {
                throw new SQLException("No se pudo insertar la orden de venta");
            }

            ordenVenta.setId(cmd.getInt("p_id"));

            LineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
            lineaOrdenVentaDAO.insertLineas(ordenVenta.getId(), ordenVenta.getLineas());
        }
    }

    @Override
    public void update(OrdenVenta modelo) throws SQLException {
        Connection conn = TransactionsManager.getConnection();

        String sql = "{call modificar_orden_venta(?, ?, ?, ?, ?)}";

        try (CallableStatement cmd = conn.prepareCall(sql)) {
            if (modelo.getCliente() != null) {
                cmd.setInt("p_id_cliente", modelo.getCliente().getId());
            } else {
                cmd.setNull("p_id_cliente", Types.INTEGER);
            }

            if (modelo.getEmpleado() != null) {
                cmd.setInt("p_id_empleado", modelo.getEmpleado().getId());
            } else {
                cmd.setNull("p_id_empleado", Types.INTEGER);
            }

            cmd.setDouble("p_total", modelo.getTotal());
            cmd.setBoolean("p_activo", modelo.isActivo());
            cmd.setInt("p_id", modelo.getId());

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

        String sql = "{call eliminar_orden_venta(?)}";

        LineaOrdenVentaDAO lineaOrdenVentaDAO = new LineaOrdenVentaDAOImpl();
        lineaOrdenVentaDAO.deleteLineas(integer);

        try (CallableStatement cmd = conn.prepareCall(sql)) {
            cmd.setInt("p_id", integer);
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
