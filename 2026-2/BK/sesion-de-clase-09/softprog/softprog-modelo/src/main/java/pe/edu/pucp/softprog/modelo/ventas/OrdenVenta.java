package pe.edu.pucp.softprog.modelo.ventas;

import pe.edu.pucp.softprog.modelo.Registro;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OrdenVenta extends Registro {
    private Empleado empleado;
    private Cliente cliente;
    private double total;
    private List<LineaOrdenVenta> lineas;

    public OrdenVenta() {
        lineas = new ArrayList<>();
    }

    public OrdenVenta(final OrdenVenta ordenVenta) {
        if (ordenVenta == null) {
            throw new IllegalArgumentException("ordenVenta no puede ser nulo");
        }
        super(ordenVenta);
        setEmpleado(ordenVenta.getEmpleado());
        setCliente(ordenVenta.getCliente());
        setTotal(ordenVenta.getTotal());
        setLineas(ordenVenta.getLineas());
    }

    public Empleado getEmpleado() {
        return empleado != null ? new Empleado(empleado) : null;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = (empleado != null) ? new Empleado(empleado) : null;
    }

    public Cliente getCliente() {
        return cliente != null ? new Cliente(cliente) : null;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = (cliente != null) ? new Cliente(cliente) : null;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        if (total < 0) {
            throw new IllegalArgumentException("total no puede ser negativo");
        }
        this.total = total;
    }

    public List<LineaOrdenVenta> getLineas() {
        return Collections.unmodifiableList(lineas);
    }

    public void setLineas(List<LineaOrdenVenta> lineas) {
        if (lineas == null) {
            throw new IllegalArgumentException("lineas no puede ser nulo");
        }
        this.lineas = List.copyOf(lineas);
    }
}
