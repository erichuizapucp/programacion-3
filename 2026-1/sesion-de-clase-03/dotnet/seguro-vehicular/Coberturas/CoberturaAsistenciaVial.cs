using Seguros;

namespace Coberturas;

public class CoberturaAsistenciaVial : SeguroDecorador
{
    public CoberturaAsistenciaVial(ICotizable seguro) : base(seguro)
    {
    }

    public override double CalcularCosto()
    {
        return this.seguro.CalcularCosto() + 100.00;
    }

    public override string Descripcion()
    {
        return this.seguro.Descripcion() + ", asistencia vial";
    }
}
