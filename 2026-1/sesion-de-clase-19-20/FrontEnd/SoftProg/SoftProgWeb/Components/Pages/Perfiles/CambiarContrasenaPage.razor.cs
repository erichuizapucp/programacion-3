using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using SoftProgWeb.Servicios.Cuentas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Perfiles;

public partial class CambiarContrasenaPage : ComponentBase {
    [Inject] private ICuentasUsuarioServiceClient CuentaUsuarioServiceClient { get; set; } = default!;
    [Inject] private AuthenticationStateProvider AuthenticationStateProvider { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private SolicitudCambioContrasenaViewModel? Solicitud { get; set; } = new SolicitudCambioContrasenaViewModel();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/Home");

    private async Task EjecutarCambioContrasena() {
        MensajeResultado = string.Empty;
        OperacionExitosa = false;

        Solicitud ??= new SolicitudCambioContrasenaViewModel();

        if (string.IsNullOrWhiteSpace(Solicitud.ContrasenaActual)) {
            MensajeResultado = "Debe ingresar su contraseña actual";
            return;
        }

        if (string.IsNullOrWhiteSpace(Solicitud.NuevaContrasena)) {
            MensajeResultado = "Debe ingresar una nueva contraseña";
            return;
        }

        if (!string.Equals(Solicitud.NuevaContrasena, Solicitud.ConfirmarContrasena, StringComparison.Ordinal)) {
            MensajeResultado = "La confirmación de contraseña no coincide";
            return;
        }

        try {
            var authState = await AuthenticationStateProvider.GetAuthenticationStateAsync();
            var usuario = authState.User.Identity?.Name ?? string.Empty;

            var cuenta = CuentaUsuarioServiceClient.ObtenerPorUsername(usuario);

            if (cuenta is null) {
                throw new InvalidOperationException("No se encontro la cuenta del usuario autenticado.");
            }

            if (!CuentaUsuarioServiceClient.Login(usuario, Solicitud.ContrasenaActual)) {
                throw new InvalidOperationException("La contrasena actual es incorrecta.");
            }

            cuenta.Password = Solicitud.NuevaContrasena;
            CuentaUsuarioServiceClient.Guardar(cuenta, Estado.Modificado);
            OperacionExitosa = true;
            MensajeResultado = "Contrasena actualizada correctamente.";
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo cambiar la contrasena.";
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
