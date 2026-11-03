using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IClienteService {
        List<ClienteViewModel> FindAll();
        ClienteViewModel? FindById(int id);
        void Insert(ClienteViewModel modelo);
        void Update(ClienteViewModel modelo);
        void Delete(int id);
        List<ClienteViewModel> FilterByName(string nombre);
        ClienteViewModel? FindByCuenta(string userName);
        void Registrar(RegistroClienteViewModel registro);
    }
}
