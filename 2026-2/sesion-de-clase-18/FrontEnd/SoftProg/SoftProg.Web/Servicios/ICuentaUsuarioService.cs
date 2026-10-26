using SoftProg.Web.ViewModels;

namespace SoftProg.Web.Servicios {
    public interface ICuentaUsuarioService {
        List<CuentaUsuarioViewModel> FindAll();
        CuentaUsuarioViewModel? FindById(int id);
        void Insert(CuentaUsuarioViewModel modelo);
        void Update(CuentaUsuarioViewModel modelo);
        void Delete(int id);
        CuentaUsuarioViewModel? FindByUserName(string userName);
        bool Autenticar(string userName, string password);
        void CambiarPassword(int id, string passwordActual, string passwordNueva);
    }
}
