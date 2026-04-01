package pe.edu.softseguros.seguros;

import pe.edu.softseguros.modelo.Cotizable;
import pe.edu.softseguros.modelo.Marca;
import pe.edu.softseguros.modelo.Vehiculo;

public class SeguroBasico implements Cotizable {

    protected Vehiculo vehiculo;

    public SeguroBasico(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @Override
    public double CalcularCosto() {
        double costo = 500.00; // Costo Base

        if (this.vehiculo.getAntiguedad() > 10) {
            costo += 200.00;
        } else if (this.vehiculo.getAntiguedad() > 5) {
            costo += 100.00;
        }

        if (this.vehiculo.getMarca() == Marca.BMW
                || this.vehiculo.getMarca() == Marca.Audi
                || this.vehiculo.getMarca() == Marca.Mercedez) {
            costo += 100.00;
        }

        return costo;
    }

    @Override
    public Vehiculo Vehiculo() {
        return this.vehiculo;
    }

    @Override
    public String Descripcion() {
        return "Seguro basico vehicular";
    }

    @Override
    public String toString() {
        return String.format("Precio: %.2f, Descripción: %s",
                this.CalcularCosto(), this.Descripcion());
    }
}
