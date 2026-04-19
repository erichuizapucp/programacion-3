package pe.edu.pucp.softprog.modelo.rrhh;

import pe.edu.pucp.softprog.modelo.Registro;

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
        return "Area{" +
                "id=" + getId() +
                ", activo=" + isActivo() +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
