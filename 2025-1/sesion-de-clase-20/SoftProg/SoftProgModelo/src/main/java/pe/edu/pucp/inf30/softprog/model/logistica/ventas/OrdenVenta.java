package pe.edu.pucp.inf30.softprog.model.logistica.ventas;

import java.util.Date;
import java.util.List;
import pe.edu.pucp.inf30.softprog.model.ModeloBase;
import pe.edu.pucp.inf30.softprog.model.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.model.rrhh.Empleado;

/**
 *
 * @author eric
 */
public class OrdenVenta extends ModeloBase {
    private Cliente cliente;
    private Empleado empleado;
    private List<LineaOrdenVenta> lineasOrdenVenta;
    private double total;
    private Date fechaHora;
    
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

    public List<LineaOrdenVenta> getLineasOrdenVenta() {
        return lineasOrdenVenta;
    }

    public void setLineasOrdenVenta(List<LineaOrdenVenta> lineasOrdenVenta) {
        this.lineasOrdenVenta = lineasOrdenVenta;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }
}