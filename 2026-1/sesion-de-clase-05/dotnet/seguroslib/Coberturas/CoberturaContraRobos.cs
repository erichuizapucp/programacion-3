namespace Coberturas;

using Modelo;

public class CoberturaContraRobos : SeguroDecorador
{
    public CoberturaContraRobos(ICotizable seguro) : base(seguro)
    {
    }

    public override double CalcularCosto()
    {
        double costo = 100.00;

        if (this.seguro.Vehiculo().Marca == Marca.Toyota)
        {
            costo += 100.00;
        }

        return this.seguro.CalcularCosto() + costo;
    }

    public override string Descripcion()
    {
        return this.seguro.Descripcion() + ", cobertura contra robos";
    }
}
