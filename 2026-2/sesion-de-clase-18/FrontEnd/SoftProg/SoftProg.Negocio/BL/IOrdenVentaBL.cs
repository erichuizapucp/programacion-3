using SoftProg.Modelo.Ventas;

namespace SoftProg.Negocio.BL {
    public interface IOrdenVentaBL : IRegistroBL<OrdenVenta, int> {
        List<OrdenVenta> FindByCuenta(string userName);
    }
}
