package pe.edu.segurosoft.coberturas;

import pe.edu.segurosoft.modelo.Cotizable;

public class CoberturaContraDesastresNaturales extends SeguroDecorador {

    public CoberturaContraDesastresNaturales(Cotizable seguro) {
        super(seguro);
    }

    @Override
    public double CalcularCosto() {
        return this.seguro.CalcularCosto() + 300.00;
    }

    @Override
    public String Descripcion() {
        return this.seguro.Descripcion() + ", cobertura contra desastres naturales";
    }
}
