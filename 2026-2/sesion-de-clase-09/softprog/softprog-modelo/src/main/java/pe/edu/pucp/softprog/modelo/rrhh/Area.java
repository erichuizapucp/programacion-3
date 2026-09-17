package pe.edu.pucp.softprog.modelo.rrhh;

import pe.edu.pucp.softprog.modelo.Registro;

public class Area extends Registro {
    private String nombre;

    public Area() {
    }

    public Area(final Area area) {
        if (area == null) {
            throw new IllegalArgumentException("area no puede ser nulo");
        }

        super(area);
        setNombre(area.getNombre());
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("\t%-20s", nombre);
    }
}
