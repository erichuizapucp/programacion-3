package pe.edu.pucp.inf30.softprog.model.logistica.almacen;

import pe.edu.pucp.inf30.softprog.model.ModeloBase;

/**
 *
 * @author eric
 */
public class Producto extends ModeloBase {
    private String nombre;
    private String unidadMedida;
    private double precio;
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
