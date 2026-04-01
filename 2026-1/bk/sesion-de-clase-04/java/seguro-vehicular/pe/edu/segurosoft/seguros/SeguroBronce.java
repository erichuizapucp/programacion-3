package pe.edu.segurosoft.seguros;

import pe.edu.segurosoft.modelo.Vehiculo;
import pe.edu.segurosoft.modelo.Marca;

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
