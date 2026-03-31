
public class CoberturaAsistenciaVial extends SeguroDecorador {

    public CoberturaAsistenciaVial(Cotizable seguro) {
        super(seguro);
    }

    @Override
    public double CalcularCosto() {
        return this.seguro.CalcularCosto() + 100.00;
    }

    @Override
    public String Descripcion() {
        return this.seguro.Descripcion() + ", asistencia vial";
    }
}
