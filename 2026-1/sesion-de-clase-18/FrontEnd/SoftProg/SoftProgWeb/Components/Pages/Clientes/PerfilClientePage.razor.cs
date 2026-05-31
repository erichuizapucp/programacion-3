using Microsoft.AspNetCore.Components;
using SoftProgModelo.Modelos;
using SoftProgWeb.Servicios.Clientes;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Clientes;

public partial class PerfilClientePage : ComponentBase {
    [Inject] private IClientesService ClienteService { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "id")]
    public int? Id { get; set; }

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private ClienteViewModel Cliente { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/ListarClientes");

    protected override void OnParametersSet() {
        if (Id is > 0) {
            try {
                Cliente = ClienteService.Obtener(Id.Value) ?? throw new InvalidOperationException();
            }
            catch {
                OperacionExitosa = false;
                MensajeResultado = "El cliente no existe.";
            }
        }
    }

    private void Guardar() {
        if (string.IsNullOrWhiteSpace(Cliente.Dni) || string.IsNullOrWhiteSpace(Cliente.Nombre)) {
            OperacionExitosa = false;
            MensajeResultado = "Debe ingresar DNI y nombre.";
            return;
        }

        try {
            ClienteService.Guardar(Cliente, Estado.Modificado);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }

    }

    private void Volver() {
        NavigationManager.NavigateTo(RutaRetorno);
    }

    private string ObtenerRutaRetorno(string fallback) {
        if (string.IsNullOrWhiteSpace(ReturnUrl)) {
            return fallback;
        }

        return ReturnUrl.StartsWith('/') ? ReturnUrl : fallback;
    }
}
