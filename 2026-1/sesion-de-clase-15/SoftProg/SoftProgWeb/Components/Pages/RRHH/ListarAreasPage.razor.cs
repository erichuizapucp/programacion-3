using Microsoft.AspNetCore.Components;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Components.Pages.RRHH;

public partial class ListarAreasPage : ComponentBase {
    [Inject] private IAreaBo AreaBo { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private List<AreaViewModel> Areas { get; set; } = [];
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }

    protected override void OnInitialized() {
        CargarAreas();
    }

    private void CargarAreas() {
        try {
            var areas = AreaBo.Listar();
            Areas = [.. areas.Select(AreaViewModelMapper.ToViewModel)];
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
            AreaBo.Eliminar(idArea);
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
}
