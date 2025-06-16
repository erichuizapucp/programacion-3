package pe.edu.pucp.inf30.softprog.model;

public class ModeloBase {
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