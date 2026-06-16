using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Cuentas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Perfiles;

public partial class ListarCuentasPage : ComponentBase {
    [Inject] private ICuentasUsuarioServiceClient CuentaUsuarioServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private const int TamanoPagina = 5;

    private List<CuentaUsuarioViewModel> Cuentas { get; set; } = [];
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private int PaginaActual { get; set; } = 1;

    private IEnumerable<CuentaUsuarioViewModel> CuentasPaginadas =>
        Cuentas.Skip((PaginaActual - 1) * TamanoPagina).Take(TamanoPagina);

    private int TotalRegistros => Cuentas.Count;
    private int TotalPaginas => Math.Max(1, (int)Math.Ceiling((double)TotalRegistros / TamanoPagina));
    private int InicioRegistro => TotalRegistros == 0 ? 0 : ((PaginaActual - 1) * TamanoPagina) + 1;
    private int FinRegistro => Math.Min(PaginaActual * TamanoPagina, TotalRegistros);

    protected override void OnInitialized() {
        CargarCuentas();
    }

    private void CargarCuentas() {
        try {
            Cuentas = CuentaUsuarioServiceClient.Listar();
            ReiniciarPaginacion();
            MensajeResultado = string.Empty;
        }
        catch {
            Cuentas = [];
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void RegistrarCuenta() {
        NavigationManager.NavigateTo("/GestionarCuenta");
    }

    private void ModificarCuenta(int id) {
        NavigationManager.NavigateTo($"/GestionarCuenta?id={id}");
    }

    private void EliminarCuenta(int id) {
        try {
            CuentaUsuarioServiceClient.Eliminar(id);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            CargarCuentas();
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
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
