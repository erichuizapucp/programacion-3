using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using System.Globalization;
using SoftProgWeb.Servicios.Ventas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Ventas;

public partial class ListarOrdenesVentaPage : ComponentBase {
    private static readonly CultureInfo CulturaSoles = CultureInfo.GetCultureInfo("es-PE");
    private const int TamanoPagina = 5;

    [CascadingParameter] private Task<AuthenticationState>? AuthenticationStateTask { get; set; }
    [Inject] private IOrdenesVentaServiceClient OrdenVentaServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private List<OrdenVentaViewModel> Ordenes { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private int PaginaActual { get; set; } = 1;

    private IEnumerable<OrdenVentaViewModel> OrdenesPaginadas =>
        Ordenes.Skip((PaginaActual - 1) * TamanoPagina).Take(TamanoPagina);

    private int TotalRegistros => Ordenes.Count;
    private int TotalPaginas => Math.Max(1, (int)Math.Ceiling((double)TotalRegistros / TamanoPagina));
    private int InicioRegistro => TotalRegistros == 0 ? 0 : ((PaginaActual - 1) * TamanoPagina) + 1;
    private int FinRegistro => Math.Min(PaginaActual * TamanoPagina, TotalRegistros);

    protected override async Task OnInitializedAsync() {
        var authState = AuthenticationStateTask is null
            ? null
            : await AuthenticationStateTask;

        var user = authState?.User;
        var usuarioActual = user?.Identity?.Name ?? string.Empty;
        var esCliente = user?.IsInRole("Cliente") == true;

        CargarOrdenes(esCliente, usuarioActual);
    }

    private void CargarOrdenes(bool esCliente, string usuarioActual) {
        try {
            Ordenes = esCliente
                ? OrdenVentaServiceClient.ListarPorCuenta(usuarioActual)
                : OrdenVentaServiceClient.Listar();
            ReiniciarPaginacion();
            MensajeResultado = string.Empty;
        }
        catch {
            Ordenes = [];
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void RegistrarOrden() {
        NavigationManager.NavigateTo("/GestionarOrdenesVenta");
    }

    private void ModificarOrden(int id) {
        NavigationManager.NavigateTo($"/GestionarOrdenesVenta?id={id}");
    }

    private void VerDetalleOrden(int id) {
        NavigationManager.NavigateTo($"/VerOrdenVenta?id={id}&returnUrl=/ListarOrdenesVenta");
    }

    private void EliminarOrden(int id) {
        try {
            OrdenVentaServiceClient.Eliminar(id);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            NavigationManager.NavigateTo(NavigationManager.Uri, forceLoad: true);
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private static string FormatearMoneda(double monto) {
        return monto.ToString("C2", CulturaSoles);
    }

    private void PaginaAnterior() {
        if (PaginaActual > 1) {
            PaginaActual--;
        }
    }

    private void PaginaSiguiente() {
        if (PaginaActual < TotalPaginas) {
            PaginaActual++;
        }
    }

    private void ReiniciarPaginacion() {
        PaginaActual = 1;
    }
}
