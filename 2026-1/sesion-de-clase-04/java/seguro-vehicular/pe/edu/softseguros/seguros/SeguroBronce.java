package pe.edu.softseguros.seguros;

import pe.edu.softseguros.modelo.Marca;
import pe.edu.softseguros.modelo.Vehiculo;

public class SeguroBronce extends SeguroBasico {

    public SeguroBronce(Vehiculo vehiculo) {
        super(vehiculo);
    }

    @Override
    public double CalcularCosto() {
        double costo = 100.00;

        if (this.vehiculo.getMarca() == Marca.Toyota) {
            costo += 100.00;
        }

        return super.CalcularCosto() + costo;
    }

    @Override
    public String Descripcion() {
        return super.Descripcion() + " + cobertura contra robos";
    }
}
