using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface IClienteService {
        Task<List<ClienteViewModel>> FindAll();
        Task<ClienteViewModel?> FindById(int id);
        Task Insert(ClienteViewModel modelo);
        Task Update(ClienteViewModel modelo);
        Task Delete(int id);
        Task<List<ClienteViewModel>> FilterByName(string nombre);
        Task<ClienteViewModel?> FindByCuenta(string userName);
        Task Registrar(RegistroClienteViewModel registro);
    }
}
