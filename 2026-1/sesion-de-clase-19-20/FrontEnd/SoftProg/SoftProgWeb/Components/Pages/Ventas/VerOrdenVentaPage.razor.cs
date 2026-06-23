using Microsoft.AspNetCore.Components;
using System.Globalization;
using SoftProgWeb.Servicios.Ventas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Ventas;

public partial class VerOrdenVentaPage : ComponentBase {
    private const double TasaIgv = 0.18;
    private static readonly CultureInfo CulturaSoles = CultureInfo.GetCultureInfo("es-PE");

    [Inject] private IOrdenesVentaServiceClient OrdenVentaServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "id")]
    public int? Id { get; set; }

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private string IdOrden { get; set; } = string.Empty;
    private string Cliente { get; set; } = string.Empty;
    private string DniCliente { get; set; } = string.Empty;

    private List<LineaOrdenVentaViewModel> LineasOrden { get; set; } = new();
    private string RutaRetorno => ObtenerRutaRetorno("/ListarOrdenesVenta");

    private double SubtotalOrden => LineasOrden.Sum(linea => linea.SubTotal);
    private double TotalOrden => Math.Round(SubtotalOrden * (1 + TasaIgv), 2, MidpointRounding.AwayFromZero);
    private double IgvOrden => Math.Round(TotalOrden - SubtotalOrden, 2, MidpointRounding.AwayFromZero);

    protected override void OnParametersSet() {
        if (Id is not > 0) {
            IdOrden = string.Empty;
            Cliente = string.Empty;
            DniCliente = string.Empty;
            LineasOrden = [];
            return;
        }

        var ordenViewModel = OrdenVentaServiceClient.Obtener(Id.Value) ?? throw new InvalidOperationException();

        IdOrden = ordenViewModel.Id.ToString();
        Cliente = $"{ordenViewModel.Cliente?.Nombre} {ordenViewModel.Cliente?.ApellidoPaterno}".Trim();
        DniCliente = ordenViewModel.Cliente?.Dni ?? string.Empty;
        LineasOrden = ordenViewModel.Lineas;
    }

    private static string FormatearMoneda(double monto) {
        return monto.ToString("C2", CulturaSoles);
    }

    private void Volver() {
        NavigationManager.NavigateTo(RutaRetorno);
    }

    private string ObtenerRutaRetorno(string fallback) {
        if (string.IsNullOrWhiteSpace(ReturnUrl)) {
            return fallback;
        }

        return ReturnUrl.StartsWith('/') ? ReturnUrl : fallback;
    }
}
