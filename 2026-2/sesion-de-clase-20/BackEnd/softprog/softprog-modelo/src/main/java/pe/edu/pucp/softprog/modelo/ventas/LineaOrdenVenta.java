package pe.edu.pucp.softprog.modelo.ventas;

import pe.edu.pucp.softprog.modelo.Registro;
import pe.edu.pucp.softprog.modelo.almacen.Producto;

public class LineaOrdenVenta extends Registro {
    private Producto producto;
    private int cantidad;
    private double subTotal;

    public LineaOrdenVenta() {
    }

    public LineaOrdenVenta(final LineaOrdenVenta lineaOrdenVenta) {
        if (lineaOrdenVenta == null) {
            throw new IllegalArgumentException("lineaOrdenVenta no puede ser nulo");
        }
        super(lineaOrdenVenta);
        setProducto(lineaOrdenVenta.getProducto());
        setCantidad(lineaOrdenVenta.getCantidad());
        setSubTotal(lineaOrdenVenta.getSubTotal());
    }

    public Producto getProducto() {
        return new Producto(producto);
    }

    public void setProducto(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("producto no puede ser nulo");
        }

        this.producto = new Producto(producto);
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
