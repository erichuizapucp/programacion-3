using Microsoft.AspNetCore.Components;
using SoftProgNegocio.Bo.Clientes;
using SoftProgWeb.ViewModels;
using SoftProgWeb.ViewModels.Mappers;

namespace SoftProgWeb.Components.Pages.Clientes;

public partial class ListarClientesPage : ComponentBase {
    [Inject] private IClienteBo ClienteBo { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "dni")]
    public string? DniQuery { get; set; }

    private string DniBusqueda { get; set; } = string.Empty;
    private List<ClienteViewModel> Clientes { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }

    protected override void OnParametersSet() {
        DniBusqueda = DniQuery?.Trim() ?? string.Empty;
        AplicarFiltroPorDni();
    }

    private void CargarClientes() {
        try {
            var clientes = ClienteBo.Listar();
            Clientes = [.. clientes.Select(ClienteViewModelMapper.ToViewModel)];
            MensajeResultado = string.Empty;
        }
        catch {
            Clientes = [];
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void Buscar() {
        var dni = DniBusqueda.Trim();
        var destino = string.IsNullOrWhiteSpace(dni)
            ? "/ListarClientes"
            : $"/ListarClientes?dni={Uri.EscapeDataString(dni)}";

        NavigationManager.NavigateTo(destino);
    }

    private void LimpiarBusqueda() {
        DniBusqueda = string.Empty;
        NavigationManager.NavigateTo("/ListarClientes");
    }

    private void Eliminar(int id) {
        try {
            ClienteBo.Eliminar(id);
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
            CargarClientes();
            return;
        }

        try {
            var cliente = ClienteBo
                .Listar()
                .FirstOrDefault(actual => string.Equals(actual.Dni, DniBusqueda, StringComparison.OrdinalIgnoreCase));

            Clientes = cliente is null ? [] : [ClienteViewModelMapper.ToViewModel(cliente)];
        }
        catch {
            Clientes = [];
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }
}
