using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Ventas;

public interface IOrdenesVentaServiceClient : IServiceClient<OrdenVentaViewModel> {
    List<OrdenVentaViewModel> ListarPorCuenta(string cuenta);
}
