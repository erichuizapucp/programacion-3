package pe.edu.pucp.softprog.model;

/**
 *
 * @author eric
 */
public class Area {
    private int id;
    private String nombre;
    private boolean activa;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }   
}
