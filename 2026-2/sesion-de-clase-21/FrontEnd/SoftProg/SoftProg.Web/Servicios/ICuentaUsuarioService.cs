using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface ICuentaUsuarioService {
        Task<List<CuentaUsuarioViewModel>> FindAll();
        Task<CuentaUsuarioViewModel?> FindById(int id);
        Task Insert(CuentaUsuarioViewModel modelo);
        Task Update(CuentaUsuarioViewModel modelo);
        Task Delete(int id);
        Task<CuentaUsuarioViewModel?> FindByUserName(string userName);
        Task<bool> Autenticar(string userName, string password);
        Task CambiarPassword(int id, string passwordActual, string passwordNueva);
    }
}
