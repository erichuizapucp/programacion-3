namespace Seguros;

public class SeguroBasico : ICotizable
{
    protected readonly Vehiculo vehiculo;
    public SeguroBasico(Vehiculo vehiculo)
    {
        this.vehiculo = vehiculo;
    }

    public virtual double CalcularCosto()
    {
        double costo = 500.00; // Costo Base

        if (this.vehiculo.Antiguedad > 10)
        {
            costo += 200.00;
        }
        else if (this.vehiculo.Antiguedad > 5)
        {
            costo += 100.00;
        }

        if (this.vehiculo.Marca == Marca.BMW ||
            this.vehiculo.Marca == Marca.Audi ||
            this.vehiculo.Marca == Marca.Mercedez)
        {
            costo += 100.00;
        }

        return costo;
    }

    public Vehiculo Vehiculo()
    {
        return this.vehiculo;
    }

    public virtual string Descripcion()
    {
        return "Seguro basico vehicular";
    }

    public override string ToString()
    {
        return $"Precio: {this.CalcularCosto():F2}, Descripción: {this.Descripcion()}";
    }
}
