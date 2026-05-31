using Microsoft.AspNetCore.Components;
using Microsoft.AspNetCore.Components.Authorization;
using SoftProgNegocio.Bo.Almacen;
using SoftProgNegocio.Bo.Clientes;
using SoftProgNegocio.Bo.Cuentas;
using SoftProgNegocio.Bo.Rrhh;
using SoftProgNegocio.Bo.Ventas;
using SoftProgWeb.ViewModels;
using System.Globalization;

namespace SoftProgWeb.Components.Pages;

public partial class Home : ComponentBase {
    private static readonly CultureInfo CulturaSoles = CultureInfo.GetCultureInfo("es-PE");

    [CascadingParameter] private Task<AuthenticationState>? AuthenticationStateTask { get; set; }
    [Inject] private IProductoBo ProductoBo { get; set; } = default!;
    [Inject] private IEmpleadoBo EmpleadoBo { get; set; } = default!;
    [Inject] private IClienteBo ClienteBo { get; set; } = default!;
    [Inject] private IOrdenVentaBo OrdenVentaBo { get; set; } = default!;
    [Inject] private ICuentaUsuarioBo CuentaUsuarioBo { get; set; } = default!;

    private string RolActual { get; set; } = string.Empty;
    private string UsuarioActual { get; set; } = string.Empty;
    private string TituloDashboard { get; set; } = string.Empty;
    private string DescripcionDashboard { get; set; } = string.Empty;
    private string MensajeError { get; set; } = string.Empty;

    private List<DashboardMetricViewModel> Metricas { get; } = [];
    private List<DashboardQuickAccessViewModel> AccesosRapidos { get; } = [];
    private List<DashboardOrderSummaryViewModel> UltimasOrdenes { get; } = [];

    protected override async Task OnInitializedAsync() {
        var authState = AuthenticationStateTask is null
            ? null
            : await AuthenticationStateTask;

        var user = authState?.User;
        var estaAutenticado = user?.Identity?.IsAuthenticated == true;
        UsuarioActual = user?.Identity?.Name ?? string.Empty;

        if (!estaAutenticado) {
            return;
        }

        if (user!.IsInRole("Admin")) {
            RolActual = "Admin";
        }
        else if (user.IsInRole("Empleado")) {
            RolActual = "Empleado";
        }
        else {
            RolActual = "Cliente";
        }

        try {
            CargarDashboard();
        }
        catch {
            MensajeError = "No se pudo completar la operacion.";
        }
    }

    private void CargarDashboard() {
        Metricas.Clear();
        AccesosRapidos.Clear();
        UltimasOrdenes.Clear();
        MensajeError = string.Empty;

        var ordenes = new List<SoftProgModelo.Modelos.Ventas.OrdenVenta>();

        if (RolActual == "Admin") {
            var productos = LeerListaSegura(() => ProductoBo.Listar());
            var empleados = LeerListaSegura(() => EmpleadoBo.Listar());
            var clientes = LeerListaSegura(() => ClienteBo.Listar());
            ordenes = LeerListaSegura(() => OrdenVentaBo.Listar());
            var cuentas = LeerListaSegura(() => CuentaUsuarioBo.Listar());

            TituloDashboard = "Panel de administración";
            DescripcionDashboard = "Resumen general del negocio, usuarios y operaciones.";

            Metricas.Add(new DashboardMetricViewModel("Total de cuentas", cuentas.Count.ToString(), "Usuarios registrados en la plataforma", "dashboard-metric-cuentas"));
            Metricas.Add(new DashboardMetricViewModel("Total de clientes", clientes.Count.ToString(), "Clientes activos e historicos", "dashboard-metric-clientes"));
            Metricas.Add(new DashboardMetricViewModel("Total de empleados", empleados.Count.ToString(), "Equipo operativo disponible", "dashboard-metric-empleados"));
            Metricas.Add(new DashboardMetricViewModel("Productos", productos.Count.ToString(), "Catalogo disponible para ventas", "dashboard-metric-productos"));
            Metricas.Add(new DashboardMetricViewModel("Ordenes", ordenes.Count.ToString(), "Ordenes registradas en el sistema", "dashboard-metric-ordenes"));

            var totalVentas = ordenes.Sum(orden => (decimal)orden.Total);
            Metricas.Add(new DashboardMetricViewModel("Ventas (Total con IGV)", FormatearMoneda(totalVentas), "Acumulado estimado con IGV incluido", "dashboard-metric-ventas"));

            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Listar cuentas de usuario", "/ListarCuentas", "dashboard-quick-cuentas"));
            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Listar empleados", "/ListarEmpleados", "dashboard-quick-empleados"));
            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Listar ordenes de venta", "/ListarOrdenesVenta", "dashboard-quick-ordenes"));
        }
        else if (RolActual == "Empleado") {
            var productos = LeerListaSegura(() => ProductoBo.Listar());
            var clientes = LeerListaSegura(() => ClienteBo.Listar());
            ordenes = LeerListaSegura(() => OrdenVentaBo.Listar());

            TituloDashboard = "Panel de operaciones";
            DescripcionDashboard = "Vision rapida para gestion de ventas y atencion de clientes.";

            Metricas.Add(new DashboardMetricViewModel("Clientes", clientes.Count.ToString(), "Clientes disponibles para atencion", "dashboard-metric-clientes"));
            Metricas.Add(new DashboardMetricViewModel("Productos", productos.Count.ToString(), "Productos disponibles para cotizacion", "dashboard-metric-productos"));
            Metricas.Add(new DashboardMetricViewModel("Ordenes", ordenes.Count.ToString(), "Ordenes administradas por el equipo", "dashboard-metric-ordenes"));

            var totalVentas = ordenes.Sum(orden => (decimal)orden.Total);
            Metricas.Add(new DashboardMetricViewModel("Total vendido (IGV)", FormatearMoneda(totalVentas), "Resumen global de ventas", "dashboard-metric-ventas"));

            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Registrar orden de venta", "/GestionarOrdenesVenta", "dashboard-quick-registrar-orden"));
            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Listar clientes", "/ListarClientes", "dashboard-quick-clientes"));
            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Listar productos", "/ListarProductos", "dashboard-quick-productos"));
        }
        else {
            var clientes = LeerListaSegura(() => ClienteBo.Listar());
            ordenes = LeerListaSegura(() => OrdenVentaBo.Listar());

            TituloDashboard = "Mi resumen";
            DescripcionDashboard = "Estado de tu cuenta y actividad comercial reciente.";

            var ordenesCliente = ordenes
                .Where(orden => string.Equals(orden.Cliente?.CuentaUsuario?.UserName, UsuarioActual, StringComparison.OrdinalIgnoreCase))
                .ToList();

            Metricas.Add(new DashboardMetricViewModel("Mis ordenes", ordenesCliente.Count.ToString(), "Ordenes registradas a tu nombre", "dashboard-metric-mis-ordenes"));
            Metricas.Add(new DashboardMetricViewModel("Total acumulado", FormatearMoneda(ordenesCliente.Sum(orden => (decimal)orden.Total)), "Acumulado con IGV incluido", "dashboard-metric-monto"));
            Metricas.Add(new DashboardMetricViewModel("Categorias activas", clientes.Count(cliente => !string.IsNullOrWhiteSpace(cliente.Categoria.ToString())).ToString(), "Categorias disponibles para clientes", "dashboard-metric-categorias"));

            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Ver perfil", "/PerfilCliente", "dashboard-quick-perfil"));
            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Cambiar contrasena", "/CambiarContrasena", "dashboard-quick-password"));
            AccesosRapidos.Add(new DashboardQuickAccessViewModel("Listar ordenes de venta", "/ListarOrdenesVenta", "dashboard-quick-ordenes"));

            ordenes = ordenesCliente;
        }

        foreach (var orden in ordenes
                     .OrderByDescending(orden => orden.Id)
                     .Take(5)) {
            UltimasOrdenes.Add(new DashboardOrderSummaryViewModel(
                orden.Id,
                $"{orden.Cliente?.Nombre} {orden.Cliente?.ApellidoPaterno}".Trim(),
                FormatearMoneda((decimal)orden.Total),
                $"/VerOrdenVenta?id={orden.Id}&returnUrl=/Home"));
        }
    }

    private static List<T> LeerListaSegura<T>(Func<List<T>> lector) {
        try {
            return lector();
        }
        catch {
            return [];
        }
    }

    private static string FormatearMoneda(decimal monto) {
        return monto.ToString("C2", CulturaSoles);
    }
}
