using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Almacen;

namespace SoftProgModelo.Modelos.Ventas;

public class LineaOrdenVenta : Registro
{
    public Producto? Producto { get; set; }
    public int Cantidad { get; set; }
    public double SubTotal { get; set; }
}
