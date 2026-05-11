using Microsoft.AspNetCore.Components;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Components.Pages;

public partial class Home : ComponentBase {
    private string TituloDashboard { get; } = "Panel de clase";
    private string DescripcionDashboard { get; } = "Navegacion simplificada para las vistas del ejercicio.";

    private List<DashboardQuickAccessViewModel> AccesosRapidos { get; } =
    [
        new("Listar areas", "/ListarAreas", "dashboard-quick-areas"),
        new("Gestionar areas", "/GestionarAreas", "dashboard-quick-gestionar-areas"),
        new("Listar cuentas", "/ListarCuentas", "dashboard-quick-cuentas"),
        new("Gestionar cuenta", "/GestionarCuenta", "dashboard-quick-gestionar-cuenta"),
        new("Listar empleados", "/ListarEmpleados", "dashboard-quick-empleados"),
        new("Gestionar empleados", "/GestionarEmpleados", "dashboard-quick-gestionar-empleados"),
        new("Listar clientes", "/ListarClientes", "dashboard-quick-clientes")
    ];
}
