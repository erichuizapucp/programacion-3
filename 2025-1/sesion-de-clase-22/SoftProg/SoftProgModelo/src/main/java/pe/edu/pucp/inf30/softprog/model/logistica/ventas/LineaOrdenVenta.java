package pe.edu.pucp.inf30.softprog.model.logistica.ventas;

import pe.edu.pucp.inf30.softprog.model.ModeloBase;
import pe.edu.pucp.inf30.softprog.model.logistica.almacen.Producto;

/**
 *
 * @author eric
 */
public class LineaOrdenVenta extends ModeloBase {
    private OrdenVenta ordenVenta;
    private Producto producto;
    private int cantidad;
    private double subTotal;
    
    public OrdenVenta getOrdenVenta() {
        return ordenVenta;
    }

    public void setOrdenVenta(OrdenVenta ordenVenta) {
        this.ordenVenta = ordenVenta;
    }

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


//public OrdenVenta OrdenVenta { get; set; }
//public Producto Producto { get; set; }
//public int Cantidad { get; set; }
//public double SubTotal {  get; set; }