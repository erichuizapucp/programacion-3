using Microsoft.AspNetCore.Components;
using Microsoft.JSInterop;
using SoftProgWeb.Servicios.Rrhh;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.RRHH;

public partial class GestionarAreasPage : ComponentBase {
    [Inject] private IAreaServiceClient AreaServiceClient { get; set; } = default!;
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
                Area = AreaServiceClient.Obtener(Id.Value) ?? throw new InvalidOperationException();
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
            var estado = Area.Id <= 0 ? Estado.Nuevo : Estado.Modificado;
            AreaServiceClient.Guardar(Area, estado);

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
            AreaServiceClient.Eliminar(id);
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
