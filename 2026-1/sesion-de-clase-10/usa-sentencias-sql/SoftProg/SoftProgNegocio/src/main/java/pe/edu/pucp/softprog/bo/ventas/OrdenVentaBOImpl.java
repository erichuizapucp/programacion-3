package pe.edu.pucp.softprog.bo.ventas;

import pe.edu.pucp.softprog.bo.BaseBO;
import pe.edu.pucp.softprog.dao.TransactionsManager;
import pe.edu.pucp.softprog.dao.ventas.OrdenVentaDAO;
import pe.edu.pucp.softprog.dao.ventas.OrdenVentaDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.util.List;
import java.util.Objects;

public class OrdenVentaBOImpl extends BaseBO implements OrdenVentaBO {
    private final OrdenVentaDAO ordenVentaDao;

    public OrdenVentaBOImpl() {
        this.ordenVentaDao = new OrdenVentaDAOImpl();
    }

    @Override
    public List<OrdenVenta> listar() {
        return this.ordenVentaDao.leerTodos();
    }

    @Override
    public OrdenVenta obtener(int id) {
        validarIdPositivo(id, "id");
        return this.ordenVentaDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        validarIdPositivo(id, "id");
        TransactionsManager.iniciarTransaccion();
        try {
            if (!this.ordenVentaDao.eliminar(id)) {
                throw new IllegalStateException("No se pudo eliminar la orden de venta con id: " + id);
            }
            TransactionsManager.commitTransaccion();
        }
        catch (Exception ex) {
            TransactionsManager.rollbackTransaccion();
            throw ex;
        }
    }

    @Override
    public void guardar(OrdenVenta modelo, Estado estado) {
        validarOrdenVenta(modelo);
        validarEstado(estado);

        TransactionsManager.iniciarTransaccion();
        try {
            if (estado == Estado.Nuevo) {
                int id = this.ordenVentaDao.crear(modelo);
                if (id <= 0) {
                    throw new IllegalStateException("No se pudo crear la orden de venta");
                }
                modelo.setId(id);
            }
            else if (estado == Estado.Modificado) {
                validarIdPositivo(modelo.getId(), "id de la orden de venta");
                if (!this.ordenVentaDao.actualizar(modelo)) {
                    throw new IllegalStateException("No se pudo actualizar la orden de venta con id: " + modelo.getId());
                }
            }
            else {
                throw new IllegalArgumentException("Estado no soportado en guardar: " + estado);
            }
            TransactionsManager.commitTransaccion();
        }
        catch (Exception ex) {
            TransactionsManager.rollbackTransaccion();
            throw ex;
        }
    }

    private void validarOrdenVenta(OrdenVenta modelo) {
        Objects.requireNonNull(modelo, "La orden de venta es obligatoria");
        Objects.requireNonNull(modelo.getCliente(), "El cliente de la orden es obligatorio");
        validarIdPositivo(modelo.getCliente().getId(), "id de cliente");

        if (modelo.getEmpleado() != null) {
            validarIdPositivo(modelo.getEmpleado().getId(), "id de empleado");
        }

        Objects.requireNonNull(modelo.getLineas(), "Las lineas de la orden son obligatorias");
        if (modelo.getLineas().isEmpty()) {
            throw new IllegalArgumentException("La orden debe tener al menos una linea");
        }

        double totalLineas = 0;
        for (LineaOrdenVenta linea : modelo.getLineas()) {
            Objects.requireNonNull(linea, "Cada linea de la orden es obligatoria");
            Objects.requireNonNull(linea.getProducto(), "El producto de cada linea es obligatorio");
            validarIdPositivo(linea.getProducto().getId(), "id de producto");
            if (linea.getCantidad() <= 0) {
                throw new IllegalArgumentException("La cantidad de cada linea debe ser mayor a 0");
            }
            if (linea.getSubTotal() < 0) {
                throw new IllegalArgumentException("El subtotal de cada linea no puede ser negativo");
            }
            totalLineas += linea.getSubTotal();
        }

        if (modelo.getTotal() < 0) {
            throw new IllegalArgumentException("El total de la orden no puede ser negativo");
        }
        if (Math.abs(totalLineas - modelo.getTotal()) > 0.01d) {
            throw new IllegalArgumentException("El total de la orden no coincide con la suma de lineas");
        }
    }

}

