using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Clientes;
using SoftProgModelo.Modelos.Rrhh;

namespace SoftProgModelo.Modelos.Ventas;

public class OrdenVenta : Registro
{
    public double Total { get; set; }
    public DateTime Fecha { get; set; }
    public Cliente? Cliente { get; set; }
    public Empleado? Empleado { get; set; }
    public List<LineaOrdenVenta> Lineas { get; set; } = new();
}
