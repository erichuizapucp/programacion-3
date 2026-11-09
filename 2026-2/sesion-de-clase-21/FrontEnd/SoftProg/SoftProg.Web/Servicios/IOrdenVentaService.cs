using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IOrdenVentaService {
        Task<List<OrdenVentaViewModel>> FindAll();
        Task<OrdenVentaViewModel?> FindById(int id);
        Task Insert(OrdenVentaViewModel modelo);
        Task Update(OrdenVentaViewModel modelo);
        Task Delete(int id);
        Task<List<OrdenVentaViewModel>> FindByCuenta(string userName);
    }
}
