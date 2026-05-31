using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class LineaOrdenVentaViewModel {
    public int Id { get; set; }

    [Range(1, int.MaxValue, ErrorMessage = "Debe seleccionar un producto valido.")]
    public int ProductoId { get; set; }

    [StringLength(150, ErrorMessage = "El nombre del producto no puede exceder 150 caracteres.")]
    public string ProductoNombre { get; set; } = string.Empty;

    [Range(1, int.MaxValue, ErrorMessage = "La cantidad debe ser mayor a cero.")]
    public int Cantidad { get; set; }

    [Range(typeof(double), "0.01", "999999999", ErrorMessage = "El precio unitario debe ser mayor a cero.")]
    public double PrecioUnitario { get; set; }

    public double SubTotal => Cantidad * PrecioUnitario;
}
