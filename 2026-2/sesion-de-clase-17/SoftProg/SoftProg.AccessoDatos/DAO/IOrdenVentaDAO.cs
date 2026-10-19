using SoftProg.Modelo.Ventas;

namespace SoftProg.AccessoDatos.DAO {
    public interface IOrdenVentaDAO : IRegistroDAO<OrdenVenta> {
        List<OrdenVenta> FindByCuenta(string userName);
    }
}
