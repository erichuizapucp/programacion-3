using Seguros;

namespace Coberturas;

public class SeguroDecorador : ICotizable
{
    protected ICotizable seguro;

    public SeguroDecorador(ICotizable seguro)
    {
        this.seguro = seguro;
    }

    public virtual double CalcularCosto()
    {
        return this.seguro.CalcularCosto();
    }

    public virtual string Descripcion()
    {
        return this.seguro.Descripcion();
    }

    public Vehiculo Vehiculo()
    {
        return this.seguro.Vehiculo();
    }

    public override string ToString()
    {
        return $"Precio: {this.CalcularCosto():F2}, Descripción: {this.Descripcion()}";
    }
}
