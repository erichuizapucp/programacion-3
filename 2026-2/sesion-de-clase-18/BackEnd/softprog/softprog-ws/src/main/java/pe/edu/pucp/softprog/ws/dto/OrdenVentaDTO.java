package pe.edu.pucp.softprog.ws.dto;

import java.util.ArrayList;
import java.util.List;

public class OrdenVentaDTO {
    private int id;
    private boolean activo;
    private int idCliente;
    private String nombreCliente;
    private int idEmpleado;
    private String nombreEmpleado;
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

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
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
