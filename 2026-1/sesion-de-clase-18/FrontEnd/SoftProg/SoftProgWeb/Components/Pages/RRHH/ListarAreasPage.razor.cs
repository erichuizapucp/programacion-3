using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Rrhh;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.RRHH;

public partial class ListarAreasPage : ComponentBase {
    [Inject] private IAreaServiceClient AreaServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private const int TamanoPagina = 5;

    private List<AreaViewModel> Areas { get; set; } = [];
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private int PaginaActual { get; set; } = 1;

    private IEnumerable<AreaViewModel> AreasPaginadas =>
        Areas.Skip((PaginaActual - 1) * TamanoPagina).Take(TamanoPagina);

    private int TotalRegistros => Areas.Count;
    private int TotalPaginas => Math.Max(1, (int)Math.Ceiling((double)TotalRegistros / TamanoPagina));
    private int InicioRegistro => TotalRegistros == 0 ? 0 : ((PaginaActual - 1) * TamanoPagina) + 1;
    private int FinRegistro => Math.Min(PaginaActual * TamanoPagina, TotalRegistros);

    protected override void OnInitialized() {
        CargarAreas();
    }

    private void CargarAreas() {
        try {
            Areas = AreaServiceClient.Listar();
            ReiniciarPaginacion();
        }
        catch {
            Areas = [];
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void EliminarArea(int idArea) {
        if (idArea <= 0) {
            return;
        }

        try {
            AreaServiceClient.Eliminar(idArea);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";

            CargarAreas();
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void ModificarArea(int idArea) {
        NavigationManager.NavigateTo($"/GestionarAreas?id={idArea}");
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
