package pe.edu.pucp.inf30.softprog.modelo.almacen;

import pe.edu.pucp.inf30.softprog.modelo.Registro;

/**
 *
 * @author eric
 */
public class Producto extends Registro {
    private String nombre;
    private UnidadMedida unidadMedida;
    private double precio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
}
