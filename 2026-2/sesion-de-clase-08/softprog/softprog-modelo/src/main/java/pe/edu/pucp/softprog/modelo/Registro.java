package pe.edu.pucp.softprog.modelo;

public abstract class Registro {
    private int id;
    private boolean activo;

    public Registro() {
    }

    public Registro(final Registro registro) {
        if  (registro == null) {
            throw new IllegalArgumentException("registro no puede ser nulo");
        }
        setId(registro.getId());
        setActivo(registro.isActivo());
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

    @Override
    public String toString() {
        return String.format("%-5d\t%-5b", id, activo);
    }
}
