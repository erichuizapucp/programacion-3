using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Cuentas;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Perfiles;

public partial class GestionarCuentaPage : ComponentBase {
    [Inject] private ICuentasUsuarioServiceClient CuentaUsuarioServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    [SupplyParameterFromQuery(Name = "id")]
    public int? Id { get; set; }

    [SupplyParameterFromQuery(Name = "returnUrl")]
    public string? ReturnUrl { get; set; }

    private string Titulo { get; set; } = "Registrar cuenta";
    private CuentaUsuarioViewModel CuentaViewModel { get; set; } = new();
    private string PasswordActual { get; set; } = string.Empty;
    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private string RutaRetorno => ObtenerRutaRetorno("/ListarCuentas");

    protected override void OnParametersSet() {
        if (Id is > 0) {
            try {
                var cuenta = CuentaUsuarioServiceClient.Obtener(Id.Value) ?? throw new InvalidOperationException();
                CuentaViewModel = cuenta;
                PasswordActual = cuenta.Password;
                Titulo = "Modificar";
            }
            catch {
                OperacionExitosa = false;
                MensajeResultado = "La cuenta no existe.";
            }
        }
        else {
            CuentaViewModel = new CuentaUsuarioViewModel();
            PasswordActual = string.Empty;
            Titulo = "Registrar cuenta";
        }
    }

    private void Guardar() {
        MensajeResultado = string.Empty;
        OperacionExitosa = false;

        if (Id is > 0) {
            CuentaViewModel.Id = Id.Value;
        }

        try {
            var estado = CuentaViewModel.Id <= 0 ? Estado.Nuevo : Estado.Modificado;
            CuentaUsuarioServiceClient.Guardar(CuentaViewModel, estado);

            OperacionExitosa = true;
            MensajeResultado = "Operacion realizada correctamente.";
            NavigationManager.NavigateTo("/ListarCuentas");
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
