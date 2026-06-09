using Microsoft.AspNetCore.Components;
using SoftProgWeb.Servicios.Clientes;
using SoftProgWeb.Components.Pages.Clientes.Components;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages.Clientes;

public partial class RegistrarClientePage : ComponentBase {
    [Inject] private IClientesServiceClient ClienteServiceClient { get; set; } = default!;
    [Inject] private NavigationManager NavigationManager { get; set; } = default!;

    private const int TotalPasos = 3;

    private readonly List<string> ErroresPaso = [];
    private readonly RegistroClienteFormulario Formulario = new();

    private int PasoActual { get; set; }

    private string MensajeResultado { get; set; } = string.Empty;
    private bool OperacionExitosa { get; set; }
    private bool Enviando { get; set; }
    private bool RegistroCompletado { get; set; }
    private string[] TitulosPaso => ["Cuenta de usuario", "Datos personales", "Detalles del cliente"];

    private void PasoAnterior() {
        if (PasoActual <= 0) {
            return;
        }

        ErroresPaso.Clear();
        MensajeResultado = string.Empty;
        PasoActual--;
    }

    private void PasoSiguiente() {
        if (!ValidarPasoActual()) {
            return;
        }

        MensajeResultado = string.Empty;
        PasoActual++;
    }

    private bool ValidarPasoActual() {
        var errores = RegistroClienteValidador.ValidarPaso(Formulario, PasoActual);
        ErroresPaso.Clear();
        ErroresPaso.AddRange(errores);
        return errores.Count == 0;
    }

    private async Task FinalizarAsync() {
        if (!ValidarPasoActual()) {
            return;
        }

        Enviando = true;

        var clienteViewModel = new ClienteViewModel {
            Dni = Formulario.Dni.Trim(),
            Nombre = Formulario.Nombre.Trim(),
            ApellidoPaterno = Formulario.ApellidoPaterno.Trim(),
            FechaNacimiento = Formulario.FechaNacimiento ?? DateTime.UtcNow,
            Genero = Formulario.Genero,
            LineaCredito = Formulario.LineaCredito ?? 0,
            Categoria = Formulario.Categoria,
            CuentaUsuario = new CuentaUsuarioViewModel {
                UserName = Formulario.Usuario.Trim(),
                Password = Formulario.Contrasena,
                Activo = true
            },
            Activo = true
        };

        try {
            ClienteServiceClient.Guardar(clienteViewModel, Estado.Nuevo);
            OperacionExitosa = true;
            MensajeResultado = "Registro completado";
            Enviando = false;
            RegistroCompletado = true;
            await Task.Delay(TimeSpan.FromSeconds(10));
            NavigationManager.NavigateTo("/Login", forceLoad: true);
        }
        catch {
            OperacionExitosa = false;
            MensajeResultado = "No se pudo completar el registro";
            Enviando = false;
        }
    }
}
