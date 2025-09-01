package pe.edu.pucp.inf30.softprog.modelo.ventas;

import pe.edu.pucp.inf30.softprog.modelo.Registro;
import pe.edu.pucp.inf30.softprog.modelo.almacen.Producto;

/**
 *
 * @author eric
 */
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
    
}
