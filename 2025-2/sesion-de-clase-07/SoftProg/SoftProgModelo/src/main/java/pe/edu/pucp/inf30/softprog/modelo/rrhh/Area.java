package pe.edu.pucp.inf30.softprog.modelo.rrhh;

import pe.edu.pucp.inf30.softprog.modelo.Registro;

/**
 *
 * @author eric
 */
public class Area extends Registro {
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Nombre: %s, Activo: %b", this.getId(), 
                this.nombre, this.isActivo());
    }
}
