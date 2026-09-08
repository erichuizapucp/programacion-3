package pe.edu.pucp.softprog.modelo.rrhh;

public class Area {
    private int id;
    private boolean activo;
    private String nombre;

    public Area() {
    }

    public Area(final Area area) {
        if (area == null) {
            throw new IllegalArgumentException("area no puede ser nulo");
        }

        setId(area.getId());
        setActivo(area.isActivo());
        setNombre(area.getNombre());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("id no puede ser negativo");
        }
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
        return String.format("%-5d\t%-5b\t%-20s", id, activo, nombre);
    }
}
