using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Rrhh;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.RRHH;

public partial class GestionarEmpleadosPage : ComponentBase {
    [Inject] private IEmpleadosServiceClient EmpleadoServiceClient { get; set; } = default!;
    [Inject] private IAreaServiceClient AreaServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "id")]
    public int? Id { get; set; }

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private string Titulo { get; set; } = "Registrar empleado";
    private EmpleadoViewModel EmpleadoViewModel { get; set; } = new();
    private List<AreaViewModel> Areas { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/ListarEmpleados");

    protected override void OnParametersSet() {
        CargarAreas();
        MensajeResultado = string.Empty;
        OperacionExitosa = false;

        if (Id is > 0) {
            try {
                EmpleadoViewModel = EmpleadoServiceClient.Obtener(Id.Value) ?? throw new InvalidOperationException();
                Titulo = "Modificar empleado";
            }
            catch {
                OperacionExitosa = false;
                MensajeResultado = "El empleado no existe.";
            }
        }
        else {
            EmpleadoViewModel = new EmpleadoViewModel();
            Titulo = "Registrar empleado";
        }
    }

    private void CargarAreas() {
        try {
            Areas = AreaServiceClient.Listar();
        }
        catch {
            Areas = [];
        }
    }

    private void GuardarEmpleado() {
        MensajeResultado = string.Empty;
        OperacionExitosa = false;

        var areaSeleccionada = Areas.FirstOrDefault(area => area.Id == EmpleadoViewModel.AreaIdSeleccionada);
        if (areaSeleccionada is null) {
            OperacionExitosa = false;
            MensajeResultado = "Seleccione un area valida.";
            return;
        }

        try {
            var estado = EmpleadoViewModel.Id <= 0 ? Estado.Nuevo : Estado.Modificado;
            EmpleadoServiceClient.Guardar(EmpleadoViewModel, estado);

            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            NavigationManager.NavigateTo("/ListarEmpleados");
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
