using Microsoft.AspNetCore.Components;
using SoftProgModelo.Modelos;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Components.Pages.RRHH;

public partial class GestionarEmpleadosPage : ComponentBase {
    [Inject] private IEmpleadoBo EmpleadoBo { get; set; } = default!;
    [Inject] private IAreaBo AreaBo { get; set; } = default!;
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
                var empleado = EmpleadoBo.Obtener(Id.Value) ?? throw new InvalidOperationException();
                EmpleadoViewModel = EmpleadoViewModelMapper.ToViewModel(empleado);
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
            var areas = AreaBo.Listar();
            Areas = areas
                .Select(AreaViewModelMapper.ToViewModel)
                .ToList();
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
            var areaDominio = AreaViewModelMapper.ToDomain(areaSeleccionada);
            var empleado = EmpleadoViewModelMapper.ToDomain(EmpleadoViewModel, areaDominio);

            var estado = empleado.Id <= 0 ? Estado.Nuevo : Estado.Modificado;
            EmpleadoBo.Guardar(empleado, estado);

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
