using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.JSInterop;
using SoftProgWeb.Servicios.Clientes;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Clientes;

public partial class PerfilClientePage : ComponentBase {
    [CascadingParameter] private Task<AuthenticationState>? AuthenticationStateTask { get; set; }
    [Inject] private IClientesServiceClient ClienteServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;
    [Inject] private IJSRuntime JsRuntime { get; set; } = default!;
    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private string UsuarioActual { get; set; } = string.Empty;
    private ClienteViewModel Cliente { get; set; } = new();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/Home");

    protected override async Task OnParametersSetAsync() {
        var authState = AuthenticationStateTask is null
            ? null
            : await AuthenticationStateTask;

        var user = authState?.User;
        UsuarioActual = user?.Identity?.Name ?? string.Empty;
        var clienteActual = ClienteServiceClient.BuscarPorCuenta(UsuarioActual);

        if (clienteActual != null) {
            try {
                Cliente = ClienteServiceClient.Obtener(clienteActual.Id) ?? throw new InvalidOperationException();
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
            ClienteServiceClient.Guardar(Cliente, Estado.Modificado);
            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar la operacion.";
        }

    }

    private async Task Volver() {
        try {
            await JsRuntime.InvokeVoidAsync("history.back");
        }
        catch {
            NavigationManager.NavigateTo(RutaRetorno);
        }
    }

    private string ObtenerRutaRetorno(string fallback) {
        if (string.IsNullOrWhiteSpace(ReturnUrl)) {
            return fallback;
        }

        return ReturnUrl.StartsWith('/') ? ReturnUrl : fallback;
    }
}
