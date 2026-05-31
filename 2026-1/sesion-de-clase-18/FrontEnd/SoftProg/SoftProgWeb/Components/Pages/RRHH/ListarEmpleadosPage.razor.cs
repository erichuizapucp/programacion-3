using Microsoft.AspNetCore.Components;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Components.Pages.RRHH;

public partial class ListarEmpleadosPage : ComponentBase {
    [Inject] private IEmpleadoBo EmpleadoBo { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private const int TamanoPagina = 5;

    [SupplyParameterFromQuery(Name = "dni")]
    public string? DniQuery { get; set; }

    private string DniBusqueda { get; set; } = string.Empty;
    private List<EmpleadoViewModel> Empleados { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private int PaginaActual { get; set; } = 1;

    private IEnumerable<EmpleadoViewModel> EmpleadosPaginados =>
        Empleados.Skip((PaginaActual - 1) * TamanoPagina).Take(TamanoPagina);

    private int TotalRegistros => Empleados.Count;
    private int TotalPaginas => Math.Max(1, (int)Math.Ceiling((double)TotalRegistros / TamanoPagina));
    private int InicioRegistro => TotalRegistros == 0 ? 0 : ((PaginaActual - 1) * TamanoPagina) + 1;
    private int FinRegistro => Math.Min(PaginaActual * TamanoPagina, TotalRegistros);

    protected override void OnParametersSet() {
        DniBusqueda = DniQuery?.Trim() ?? string.Empty;
        AplicarFiltroPorDni();
    }

    private void CargarEmpleados() {
        try {
            var empleados = EmpleadoBo.Listar();
            Empleados = [.. empleados.Select(empleado => EmpleadoViewModelMapper.ToViewModel(empleado))];
            ReiniciarPaginacion();
            MensajeResultado = string.Empty;
        }
        catch {
            Empleados = [];
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void Buscar() {
        var dni = DniBusqueda.Trim();
        var destino = string.IsNullOrWhiteSpace(dni)
            ? "/ListarEmpleados"
            : $"/ListarEmpleados?dni={Uri.EscapeDataString(dni)}";

        NavigationManager.NavigateTo(destino);
    }

    private void LimpiarBusqueda() {
        DniBusqueda = string.Empty;
        NavigationManager.NavigateTo("/ListarEmpleados");
    }

    private void RegistrarEmpleado() {
        NavigationManager.NavigateTo("/GestionarEmpleados");
    }

    private void ModificarEmpleado(int id) {
        NavigationManager.NavigateTo($"/GestionarEmpleados?id={id}");
    }

    private void EliminarEmpleado(int id) {
        try {
            EmpleadoBo.Eliminar(id);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            AplicarFiltroPorDni();
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void AplicarFiltroPorDni() {
        MensajeResultado = string.Empty;

        if (string.IsNullOrWhiteSpace(DniBusqueda)) {
            CargarEmpleados();
            return;
        }

        try {
            var empleado = EmpleadoBo
                .Listar()
                .FirstOrDefault(actual => string.Equals(actual.Dni, DniBusqueda, StringComparison.OrdinalIgnoreCase));

            Empleados = empleado is null ? [] : [EmpleadoViewModelMapper.ToViewModel(empleado)];
            ReiniciarPaginacion();
        }
        catch {
            Empleados = [];
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
