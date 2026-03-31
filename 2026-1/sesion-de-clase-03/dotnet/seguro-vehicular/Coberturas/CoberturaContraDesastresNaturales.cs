using Seguros;

namespace Coberturas;

public class CoberturaContraDesastresNaturales : SeguroDecorador
{
    public CoberturaContraDesastresNaturales(ICotizable seguro) : base(seguro)
    {
    }

    public override double CalcularCosto()
    {
        return this.seguro.CalcularCosto() + 300.00;
    }

    public override string Descripcion()
    {
        return this.seguro.Descripcion() + ", cobertura contra desastres naturales";
    }
}
