package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.OrdenVentaBL;
import pe.edu.pucp.softprog.dao.ClienteDAO;
import pe.edu.pucp.softprog.dao.EmpleadoDAO;
import pe.edu.pucp.softprog.dao.OrdenVentaDAO;
import pe.edu.pucp.softprog.dao.ProductoDAO;
import pe.edu.pucp.softprog.dao.impl.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.impl.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.ProductoDAOImpl;
import pe.edu.pucp.softprog.dao.impl.ventas.OrdenVentaDAOImpl;
import pe.edu.pucp.softprog.dao.transacciones.TransactionsManager;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdenVentaBLImpl implements OrdenVentaBL {
    private static final double TASA_IGV = 0.18;

    private final OrdenVentaDAO ordenVentaDAO = new OrdenVentaDAOImpl();
    private final ProductoDAO productoDAO = new ProductoDAOImpl();
    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl();

    @Override
    public List<OrdenVenta> findAll() throws BLException {
        try {
            return ordenVentaDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar las órdenes de venta", e);
        }
    }

    @Override
    public OrdenVenta findById(Integer id) throws BLException {
        try {
            return ordenVentaDAO.findById(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar la orden de venta", e);
        }
    }

    @Override
    public List<OrdenVenta> findByCuenta(String userName) throws BLException {
        try {
            return ordenVentaDAO.findByCuenta(userName);
        } catch (SQLException e) {
            throw new BLException("No se pudo listar las órdenes de venta de la cuenta", e);
        }
    }

    @Override
    public void insert(OrdenVenta ordenVenta) throws BLException {
        validarYCalcular(ordenVenta);
        Cliente cliente = buscarCliente(ordenVenta.getCliente().getId());
        ordenVenta.setCliente(cliente);
        if (ordenVenta.getEmpleado() != null) {
            ordenVenta.setEmpleado(buscarEmpleado(ordenVenta.getEmpleado().getId()));
        }
        validarLineaCredito(ordenVenta.getTotal(), cliente.getLineaCredito());

        TransactionsManager.iniciar();
        try {
            ordenVentaDAO.insert(ordenVenta);
            clienteDAO.updateLineaCredito(cliente.getId(), cliente.getLineaCredito() - ordenVenta.getTotal());
            TransactionsManager.commit();
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo registrar la orden de venta", e);
        }
    }

    @Override
    public void update(OrdenVenta ordenVenta) throws BLException {
        validarYCalcular(ordenVenta);
        OrdenVenta ordenAnterior = buscarOrden(ordenVenta.getId());
        Cliente clienteAnterior = buscarCliente(ordenAnterior.getCliente().getId());
        Cliente clienteNuevo = buscarCliente(ordenVenta.getCliente().getId());
        ordenVenta.setCliente(clienteNuevo);
        if (ordenVenta.getEmpleado() != null) {
            ordenVenta.setEmpleado(buscarEmpleado(ordenVenta.getEmpleado().getId()));
        }

        double lineaCreditoAnteriorDevuelta = clienteAnterior.getLineaCredito() + ordenAnterior.getTotal();
        double lineaCreditoDisponible;
        if (clienteNuevo.getId() == clienteAnterior.getId()) {
            lineaCreditoDisponible = lineaCreditoAnteriorDevuelta;
        } else {
            lineaCreditoDisponible = clienteNuevo.getLineaCredito();
        }
        validarLineaCredito(ordenVenta.getTotal(), lineaCreditoDisponible);

        TransactionsManager.iniciar();
        try {
            ordenVentaDAO.update(ordenVenta);
            clienteDAO.updateLineaCredito(clienteAnterior.getId(), lineaCreditoAnteriorDevuelta);
            clienteDAO.updateLineaCredito(clienteNuevo.getId(), lineaCreditoDisponible - ordenVenta.getTotal());
            TransactionsManager.commit();
        } catch (SQLException e) {
            TransactionsManager.rollback();
            throw new BLException("No se pudo actualizar la orden de venta", e);
        }
    }

    @Override
    public void delete(Integer id) throws BLException {
        OrdenVenta ordenAnterior = buscarOrden(id);
        Cliente cliente = buscarCliente(ordenAnterior.getCliente().getId());

        TransactionsManager.iniciar();
        try {
            ordenVentaDAO.delete(id);
            clienteDAO.updateLineaCredito(cliente.getId(), cliente.getLineaCredito() + ordenAnterior.getTotal());
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
        double subTotalOrden = 0.0;
        for (LineaOrdenVenta linea : orden.getLineas()) {
            if (linea.getCantidad() < 1) {
                throw new BLException("La cantidad de cada línea debe ser al menos 1");
            }

            Producto producto = buscarProducto(linea.getProducto().getId());
            double subTotal = producto.getPrecio() * linea.getCantidad();

            LineaOrdenVenta lineaCalculada = new LineaOrdenVenta(linea);
            lineaCalculada.setProducto(producto);
            lineaCalculada.setSubTotal(subTotal);
            lineasCalculadas.add(lineaCalculada);
            subTotalOrden += subTotal;
        }

        double igv = Math.round(subTotalOrden * TASA_IGV * 100.0) / 100.0;
        double total = subTotalOrden + igv;

        orden.setLineas(lineasCalculadas);
        orden.setTotal(total);
    }

    private void validarLineaCredito(double total, double lineaCreditoDisponible) throws BLException {
        if (total > lineaCreditoDisponible) {
            throw new BLException(String.format(
                    "El total de la orden (S/ %.2f) excede la línea de crédito disponible del cliente (S/ %.2f)",
                    total, lineaCreditoDisponible));
        }
    }

    private OrdenVenta buscarOrden(int idOrden) throws BLException {
        try {
            OrdenVenta ordenVenta = ordenVentaDAO.findById(idOrden);
            if (ordenVenta == null) {
                throw new BLException("No existe la orden de venta con id " + idOrden);
            }
            return ordenVenta;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar la orden de venta", e);
        }
    }

    private Cliente buscarCliente(int idCliente) throws BLException {
        try {
            Cliente cliente = clienteDAO.findById(idCliente);
            if (cliente == null) {
                throw new BLException("No existe el cliente con id " + idCliente);
            }
            return cliente;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el cliente", e);
        }
    }

    private Empleado buscarEmpleado(int idEmpleado) throws BLException {
        try {
            Empleado empleado = empleadoDAO.findById(idEmpleado);
            if (empleado == null) {
                throw new BLException("No existe el empleado con id " + idEmpleado);
            }
            return empleado;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el empleado", e);
        }
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
