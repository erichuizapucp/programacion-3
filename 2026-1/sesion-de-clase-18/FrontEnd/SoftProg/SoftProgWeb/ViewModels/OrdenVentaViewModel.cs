using System.ComponentModel.DataAnnotations;

namespace SoftProgWeb.ViewModels;

public class OrdenVentaViewModel {
    private const double TasaIgv = 0.18;

    public int Id { get; set; }

    [Range(1, int.MaxValue, ErrorMessage = "Debe seleccionar un cliente valido.")]
    public int ClienteIdSeleccionado { get; set; }

    public ClienteViewModel? Cliente { get; set; }

    [Required(ErrorMessage = "La fecha de registro es obligatoria.")]
    public DateTime FechaRegistro { get; set; } = DateTime.UtcNow;

    [MinLength(1, ErrorMessage = "Debe agregar al menos una linea a la orden.")]
    public List<LineaOrdenVentaViewModel> Lineas { get; set; } = new();

    public bool Activo { get; set; } = true;
    public double TotalRegistrado { get; set; }
    public double Subtotal => Lineas.Sum(linea => linea.SubTotal);
    public double Total {
        get {
            if (Lineas.Count == 0 && TotalRegistrado > 0) {
                return TotalRegistrado;
            }

            return Math.Round(Subtotal * (1 + TasaIgv), 2, MidpointRounding.AwayFromZero);
        }
    }
    public double Igv => Math.Round(Total - Subtotal, 2, MidpointRounding.AwayFromZero);
}
