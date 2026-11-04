using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IEmpleadoService {
        List<EmpleadoViewModel> FindAll();
        EmpleadoViewModel? FindById(int id);
        void Insert(EmpleadoViewModel modelo);
        void Update(EmpleadoViewModel modelo);
        void Delete(int id);
        List<EmpleadoViewModel> FilterByName(string nombre);
        EmpleadoViewModel? FindByCuenta(string userName);
    }
}
