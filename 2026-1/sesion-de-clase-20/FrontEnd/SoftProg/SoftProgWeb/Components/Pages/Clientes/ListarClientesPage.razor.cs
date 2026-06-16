using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Clientes;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Clientes;

public partial class ListarClientesPage : ComponentBase {
    [Inject] private IClientesServiceClient ClienteServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private const int TamanoPagina = 5;

    [SupplyParameterFromQuery(Name = "dni")]
    public string? DniQuery { get; set; }

    private string DniBusqueda { get; set; } = string.Empty;
    private List<ClienteViewModel> Clientes { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private int PaginaActual { get; set; } = 1;

    private IEnumerable<ClienteViewModel> ClientesPaginados =>
        Clientes.Skip((PaginaActual - 1) * TamanoPagina).Take(TamanoPagina);

    private int TotalRegistros => Clientes.Count;
    private int TotalPaginas => Math.Max(1, (int)Math.Ceiling((double)TotalRegistros / TamanoPagina));
    private int InicioRegistro => TotalRegistros == 0 ? 0 : ((PaginaActual - 1) * TamanoPagina) + 1;
    private int FinRegistro => Math.Min(PaginaActual * TamanoPagina, TotalRegistros);

    protected override void OnParametersSet() {
        DniBusqueda = DniQuery?.Trim() ?? string.Empty;
        AplicarFiltroPorDni();
    }

    private void CargarClientes() {
        try {
            Clientes = ClienteServiceClient.Listar();
            ReiniciarPaginacion();
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
            ClienteServiceClient.Eliminar(id);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            AplicarFiltroPorDni();
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }
    }

    private void RegistrarCliente() {
        NavigationManager.NavigateTo("/RegistrarCliente");
    }

    private void VerPerfil(int id) {
        NavigationManager.NavigateTo($"/PerfilCliente?id={id}");
    }

    private void AplicarFiltroPorDni() {
        MensajeResultado = string.Empty;

        if (string.IsNullOrWhiteSpace(DniBusqueda)) {
            CargarClientes();
            return;
        }

        try {
            var cliente = ClienteServiceClient.BuscarPorDni(DniBusqueda);
            Clientes = cliente is null ? [] : [cliente];
            ReiniciarPaginacion();
        }
        catch {
            Clientes = [];
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
