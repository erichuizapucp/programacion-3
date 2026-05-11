using SoftProgModelo.Modelos;

namespace SoftProgModelo.Modelos.Clientes;

public class Cliente : Persona
{
    public double LineaCredito { get; set; }
    public CategoriaCliente Categoria { get; set; }
}
