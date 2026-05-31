namespace SoftProgWeb.ViewModels;

public sealed record DashboardMetricViewModel(
    string Titulo,
    string Valor,
    string Descripcion,
    string DataTestId);

public sealed record DashboardQuickAccessViewModel(
    string Texto,
    string Ruta,
    string DataTestId);

public sealed record DashboardOrderSummaryViewModel(
    int Id,
    string Cliente,
    string Total,
    string RutaDetalle);
