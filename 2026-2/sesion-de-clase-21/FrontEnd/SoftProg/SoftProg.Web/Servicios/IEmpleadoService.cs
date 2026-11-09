using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IEmpleadoService {
        Task<List<EmpleadoViewModel>> FindAll();
        Task<EmpleadoViewModel?> FindById(int id);
        Task Insert(EmpleadoViewModel modelo);
        Task Update(EmpleadoViewModel modelo);
        Task Delete(int id);
        Task<List<EmpleadoViewModel>> FilterByName(string nombre);
        Task<EmpleadoViewModel?> FindByCuenta(string userName);
    }
}
