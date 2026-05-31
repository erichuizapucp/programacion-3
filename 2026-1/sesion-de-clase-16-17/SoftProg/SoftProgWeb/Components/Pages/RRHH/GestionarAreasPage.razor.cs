using Microsoft.AspNetCore.Components;
using Microsoft.JSInterop;
using SoftProgModelo.Modelos;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Components.Pages.RRHH;

public partial class GestionarAreasPage : ComponentBase {
    [Inject] private IAreaBo AreaBo { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;
    [Inject] private IJSRuntime Js { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "id")]
    public int? Id { get; set; }

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private string Titulo { get; set; } = "Registrar area";
    private AreaViewModel Area { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/ListarAreas");

    protected override void OnParametersSet() {
        if (Id is > 0) {
            try {
                var area = AreaBo.Obtener(Id.Value) ?? throw new InvalidOperationException();
                Area = AreaViewModelMapper.ToViewModel(area);
                Titulo = "Modificar area";
            }
            catch {
                MensajeResultado = "No se pudo completar la operacion.";
                OperacionExitosa = false;
            }
        }
        else {
            Area = new AreaViewModel();
            Titulo = "Registrar area";
        }
    }

    private void Guardar() {
        MensajeResultado = string.Empty;
        OperacionExitosa = false;

        if (Id is > 0) {
            Area.Id = Id.Value;
        }

        try {
            var area = AreaViewModelMapper.ToDomain(Area);
            var estado = area.Id <= 0 ? Estado.Nuevo : Estado.Modificado;
            AreaBo.Guardar(area, estado);

            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            NavigationManager.NavigateTo("/ListarAreas");
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }

    }

    private async Task EliminarArea() {
        var id = Area.Id;
        if (id <= 0) {
            return;
        }

        bool confirm;
        try {
            confirm = await Js.InvokeAsync<bool>("confirm", "¿Confirma que desea eliminar esta área?");
        }
        catch {
            // si falla el confirm, prevenimos la eliminación accidental
            return;
        }

        if (!confirm) {
            return;
        }

        try {
            AreaBo.Eliminar(id);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            NavigationManager.NavigateTo("/ListarAreas");
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private string ObtenerRutaRetorno(string fallback) {
        if (string.IsNullOrWhiteSpace(ReturnUrl)) {
            return fallback;
        }

        return ReturnUrl.StartsWith('/') ? ReturnUrl : fallback;
    }
}
