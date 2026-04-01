package pe.edu.segurosoft.coberturas;

import pe.edu.segurosoft.modelo.Cotizable;
import pe.edu.segurosoft.modelo.Vehiculo;

public class SeguroDecorador implements Cotizable {

    protected Cotizable seguro;

    public SeguroDecorador(Cotizable seguro) {
        this.seguro = seguro;
    }

    @Override
    public double CalcularCosto() {
        return this.seguro.CalcularCosto();
    }

    @Override
    public String Descripcion() {
        return this.seguro.Descripcion();
    }

    @Override
    public Vehiculo Vehiculo() {
        return this.seguro.Vehiculo();
    }

    @Override
    public String toString() {
        return String.format("Precio: %.2f, Descripción: %s",
                this.CalcularCosto(), this.Descripcion());
    }
}
