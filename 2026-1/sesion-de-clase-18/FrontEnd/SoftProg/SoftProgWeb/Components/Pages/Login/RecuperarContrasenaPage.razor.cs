using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Cuentas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Login;

public partial class RecuperarContrasenaPage : ComponentBase {
    [Inject] private ICuentasUsuarioServiceClient CuentaUsuarioServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private SolicitudRecuperarContrasenaViewModel? Solicitud { get; set; } = new SolicitudRecuperarContrasenaViewModel();
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }

    private void Enviar() {
        Solicitud ??= new SolicitudRecuperarContrasenaViewModel();

        if (string.IsNullOrWhiteSpace(Solicitud.Correo)) {
            OperacionExitosa = false;
            MensajeResultado = "Debe ingresar un correo";
            return;
        }

        try {
            throw new NotSupportedException("La recuperacion por correo no esta disponible en esta version.");
        }
        catch (NotSupportedException) {
            OperacionExitosa = false;
            MensajeResultado = "La recuperacion por correo no esta disponible en esta version.";
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo enviar la solicitud.";
        }

    }
}
