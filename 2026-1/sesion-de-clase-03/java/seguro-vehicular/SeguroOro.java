
public class SeguroOro extends SeguroPlata {

    public SeguroOro(Vehiculo vehiculo) {
        super(vehiculo);
    }

    @Override
    public double CalcularCosto() {
        return super.CalcularCosto() + 300.00;
    }

    @Override
    public String Descripcion() {
        return super.Descripcion() + " + cobertura contra desastres naturales";
    }
}
