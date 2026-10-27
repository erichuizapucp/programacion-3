package pe.edu.pucp.softprog.ws.dto;

import java.util.ArrayList;
import java.util.List;

public class OrdenVentaDTO {
    private int id;
    private boolean activo;
    private ClienteDTO cliente;
    private EmpleadoDTO empleado;
    private double total;
    private List<LineaOrdenVentaDTO> lineas = new ArrayList<>();

    public OrdenVentaDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<LineaOrdenVentaDTO> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaOrdenVentaDTO> lineas) {
        this.lineas = lineas;
    }
}
