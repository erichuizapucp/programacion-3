namespace Seguros;

using Modelo;

public class SeguroPlata : SeguroBronce
{
    public SeguroPlata(Vehiculo vehiculo) : base(vehiculo)
    {
    }

    public override double CalcularCosto()
    {
        return base.CalcularCosto() + 100.00;
    }

    public override string Descripcion()
    {
        return base.Descripcion() + " + asistencia vial";
    }
}
