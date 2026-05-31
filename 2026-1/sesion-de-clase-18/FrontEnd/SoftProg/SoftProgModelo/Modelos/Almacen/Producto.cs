using SoftProgModelo.Modelos;

namespace SoftProgModelo.Modelos.Almacen;

public class Producto : Registro
{
    public string Nombre { get; set; } = string.Empty;
    public UnidadMedida UnidadMedida { get; set; }
    public double Precio { get; set; }
}
