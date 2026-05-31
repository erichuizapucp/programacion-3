using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Ventas;

public interface IOrdenesVentaService : ICrudService<OrdenVentaViewModel> {
    List<OrdenVentaViewModel> ListarPorCuenta(string cuenta);
}
