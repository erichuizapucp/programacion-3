using Microsoft.AspNetCore.Components;
using System.Globalization;
using SoftProgWeb.Servicios.Ventas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Ventas.Components;

public partial class DetalleOrdenVentaComponent : ComponentBase {
    private const double TasaIgv = 0.18;
    private static readonly CultureInfo CulturaSoles = CultureInfo.GetCultureInfo("es-PE");

    [Inject] private IOrdenesVentaServiceClient OrdenVentaServiceClient { get; set; } = default!;

    [Parameter] public int? OrdenId { get; set; }
    [Parameter] public EventCallback OnCerrar { get; set; }

    private string IdOrden { get; set; } = string.Empty;
    private string Cliente { get; set; } = string.Empty;
    private string DniCliente { get; set; } = string.Empty;

    private List<LineaOrdenVentaViewModel> LineasOrden { get; set; } = new();
    private double SubtotalApiOrden { get; set; }

    private double SubtotalOrden => LineasOrden.Count > 0 ? LineasOrden.Sum(linea => linea.SubTotal) : SubtotalApiOrden;
    private double IgvOrden => Math.Round(SubtotalOrden * TasaIgv, 2, MidpointRounding.AwayFromZero);
    private double TotalOrden => SubtotalOrden + IgvOrden;

    protected override void OnParametersSet() {
        if (OrdenId is not > 0) {
            IdOrden = string.Empty;
            Cliente = string.Empty;
            DniCliente = string.Empty;
            LineasOrden = [];
            SubtotalApiOrden = 0;
            return;
        }

        var ordenViewModel = OrdenVentaServiceClient.Obtener(OrdenId.Value) ?? throw new InvalidOperationException();

        IdOrden = ordenViewModel.Id.ToString();
        Cliente = $"{ordenViewModel.Cliente?.Nombre} {ordenViewModel.Cliente?.ApellidoPaterno}".Trim();
        DniCliente = ordenViewModel.Cliente?.Dni ?? string.Empty;
        LineasOrden = ordenViewModel.Lineas;
        SubtotalApiOrden = ordenViewModel.Total;
    }

    private static string FormatearMoneda(double monto) {
        return monto.ToString("C2", CulturaSoles);
    }
}
