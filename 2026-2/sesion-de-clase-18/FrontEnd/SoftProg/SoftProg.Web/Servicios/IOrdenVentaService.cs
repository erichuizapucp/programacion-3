using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IOrdenVentaService {
        List<OrdenVentaViewModel> FindAll();
        OrdenVentaViewModel? FindById(int id);
        void Insert(OrdenVentaViewModel modelo);
        void Update(OrdenVentaViewModel modelo);
        void Delete(int id);
        List<OrdenVentaViewModel> FindByCuenta(string userName);
    }
}
