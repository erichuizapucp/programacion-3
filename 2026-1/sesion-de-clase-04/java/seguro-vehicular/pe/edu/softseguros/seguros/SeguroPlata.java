package pe.edu.softseguros.seguros;

import pe.edu.softseguros.modelo.Vehiculo;

public class SeguroPlata extends SeguroBronce {

    public SeguroPlata(Vehiculo vehiculo) {
        super(vehiculo);
    }

    @Override
    public double CalcularCosto() {
        return super.CalcularCosto() + 100.00;
    }

    @Override
    public String Descripcion() {
        return super.Descripcion() + " + asistencia vial";
    }
}
