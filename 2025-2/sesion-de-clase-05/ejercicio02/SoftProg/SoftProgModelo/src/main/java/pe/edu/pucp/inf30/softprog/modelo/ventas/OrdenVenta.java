package pe.edu.pucp.inf30.softprog.modelo.ventas;

import java.util.Date;
import java.util.List;
import pe.edu.pucp.inf30.softprog.modelo.Registro;
import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;

/**
 *
 * @author eric
 */
public class OrdenVenta extends Registro {
    private double total;
    private Date fecha;
    private Cliente cliente;
    private Empleado empleado;
    private List<LineaOrdenVenta> items;

    public List<LineaOrdenVenta> getItems() {
        return items;
    }

    public void setItems(List<LineaOrdenVenta> items) {
        this.items = items;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    
}
