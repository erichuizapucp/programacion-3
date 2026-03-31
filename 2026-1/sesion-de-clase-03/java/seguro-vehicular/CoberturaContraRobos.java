
public class CoberturaContraRobos extends SeguroDecorador {

    public CoberturaContraRobos(Cotizable seguro) {
        super(seguro);
    }

    @Override
    public double CalcularCosto() {
        double costo = 100.00;

        if (this.seguro.Vehiculo().getMarca() == Marca.Toyota) {
            costo += 100.00;
        }

        return this.seguro.CalcularCosto() + costo;
    }

    @Override
    public String Descripcion() {
        return this.seguro.Descripcion() + ", cobertura contra robos";
    }
}
