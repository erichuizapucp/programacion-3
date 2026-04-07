namespace Seguros;

using Modelo;

public class SeguroBronce : SeguroBasico
{
    public SeguroBronce(Vehiculo vehiculo) : base(vehiculo)
    {
    }

    public override double CalcularCosto()
    {
        double costo = 100.00;

        if (this.vehiculo.Marca == Marca.Toyota)
        {
            costo += 100.00;
        }

        return base.CalcularCosto() + costo;
    }

    public override string Descripcion()
    {
        return base.Descripcion() + " + cobertura contra robos";
    }
}
