package pe.edu.softseguros.modelo;

public class Vehiculo {

    private Marca marca;
    private int antiguedad;

    public Vehiculo(Marca marca, int antiguedad) {
        this.marca = marca;
        this.antiguedad = antiguedad;

    }

    public Marca getMarca() {
        return marca;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }
}
