package pe.edu.pucp.softprog.modelo.ventas;

import pe.edu.pucp.softprog.modelo.Registro;
import pe.edu.pucp.softprog.modelo.almacen.Producto;

public class LineaOrdenVenta extends Registro {
    private Producto producto;
    private int cantidad;
    private double subTotal;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public String toString() {
        return "LineaOrdenVenta{" +
                "id=" + getId() +
                ", activo=" + isActivo() +
                ", producto=" + (producto != null ? producto.getId() : null) +
                ", cantidad=" + cantidad +
                ", subTotal=" + subTotal +
                '}';
    }
}
