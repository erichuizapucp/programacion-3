package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.OrdenVentaBL;
import pe.edu.pucp.softprog.dao.OrdenVentaDAO;
import pe.edu.pucp.softprog.dao.ProductoDAO;
import pe.edu.pucp.softprog.dao.impl.ProductoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.ventas.OrdenVentaDAOImpl;
import pe.edu.pucp.softprog.dao.transacciones.TransactionsManager;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenVentaBLImpl implements OrdenVentaBL {
    private final OrdenVentaDAO ordenVentaDAO = new OrdenVentaDAOImpl();
    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    @Override
    public List<OrdenVenta> findAll() throws BLException {
        TransactionsManager.iniciar();
        try {
            List<OrdenVenta> ordenes = ordenVentaDAO.findAll();
            TransactionsManager.commit();
            return ordenes;
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo listar las órdenes de venta", e);
        }
    }

    @Override
    public OrdenVenta findById(Integer id) throws BLException {
        TransactionsManager.iniciar();
        try {
            OrdenVenta orden = ordenVentaDAO.findById(id);
            TransactionsManager.commit();
            return orden;
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo recuperar la orden de venta", e);
        }
    }

    @Override
    public void insert(OrdenVenta ordenVenta) throws BLException {
        validarYCalcular(ordenVenta);

        TransactionsManager.iniciar();
        try {
            ordenVentaDAO.insert(ordenVenta);
            TransactionsManager.commit();
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo registrar la orden de venta", e);
        }
    }

    @Override
    public void update(OrdenVenta ordenVenta) throws BLException {
        validarYCalcular(ordenVenta);

        TransactionsManager.iniciar();
        try {
            ordenVentaDAO.update(ordenVenta);
            TransactionsManager.commit();
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo actualizar la orden de venta", e);
        }
    }

    @Override
    public void delete(Integer id) throws BLException {
        TransactionsManager.iniciar();
        try {
            ordenVentaDAO.delete(id);
            TransactionsManager.commit();
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo eliminar la orden de venta", e);
        }
    }

    private void validarYCalcular(OrdenVenta orden) throws BLException {
        if (orden.getLineas().isEmpty()) {
            throw new BLException("La orden de venta debe tener al menos una línea");
        }

        if (orden.getCliente() == null) {
            throw new BLException("La orden de venta debe tener un cliente");
        }

        List<LineaOrdenVenta> lineasCalculadas = new ArrayList<>();
        double total = 0.0;
        for (LineaOrdenVenta linea : orden.getLineas()) {
            if (linea.getCantidad() < 1) {
                throw new BLException("La cantidad de cada línea debe ser al menos 1");
            }

            Producto producto = buscarProducto(linea.getProducto().getId());
            double subTotal = producto.getPrecio() * linea.getCantidad();

            LineaOrdenVenta lineaCalculada = new LineaOrdenVenta(linea);
            lineaCalculada.setSubTotal(subTotal);
            lineasCalculadas.add(lineaCalculada);
            total += subTotal;
        }

        double lineaCredito = orden.getCliente().getLineaCredito();
        if (lineaCredito > 0 && total > lineaCredito) {
            throw new BLException(String.format(
                    "El total de la orden (S/ %.2f) excede la línea de crédito del cliente (S/ %.2f)",
                    total, lineaCredito));
        }

        orden.setLineas(lineasCalculadas);
        orden.setTotal(total);
    }

    private Producto buscarProducto(int idProducto) throws BLException {
        try {
            Producto producto = productoDAO.findById(idProducto);
            if (producto == null) {
                throw new BLException("No existe un producto con id " + idProducto);
            }
            return producto;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el producto de la línea", e);
        }
    }
}
