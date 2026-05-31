using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Ventas;

public interface IOrdenesVentaService : IServiceBase<OrdenVentaViewModel> {
    List<OrdenVentaViewModel> ListarPorCuenta(string cuenta);
}
