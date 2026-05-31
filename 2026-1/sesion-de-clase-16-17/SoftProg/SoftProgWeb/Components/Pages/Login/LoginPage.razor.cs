using Microsoft.AspNetCore.Components;

namespace SoftProgWeb.Components.Pages.Login;

public partial class LoginPage : ComponentBase {
    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    [SupplyParameterFromQuery(Name = "error")]
    public string? Error { get; set; }

    private string MensajeError { get; set; } = string.Empty;

    protected override void OnParametersSet() {
        MensajeError = string.Equals(Error, "1", StringComparison.Ordinal)
            ? "No se pudo iniciar sesion. Verifique sus credenciales."
            : string.Empty;
    }
}
