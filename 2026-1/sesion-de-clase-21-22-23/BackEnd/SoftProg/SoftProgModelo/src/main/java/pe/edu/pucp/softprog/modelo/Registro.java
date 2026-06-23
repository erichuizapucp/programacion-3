package pe.edu.pucp.softprog.modelo;

public abstract class Registro {
    private int id;
    private boolean activo;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
