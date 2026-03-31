namespace Seguros;

public class SeguroOro : SeguroPlata
{
    public SeguroOro(Vehiculo vehiculo) : base(vehiculo)
    {
    }

    public override double CalcularCosto()
    {
        return base.CalcularCosto() + 300.00;
    }

    public override string Descripcion()
    {
        return base.Descripcion() + " + cobertura contra desastres naturales";
    }
}
