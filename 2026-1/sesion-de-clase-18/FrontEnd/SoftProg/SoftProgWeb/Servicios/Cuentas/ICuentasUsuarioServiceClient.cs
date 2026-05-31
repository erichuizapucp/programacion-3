using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Cuentas;

public interface ICuentasUsuarioServiceClient : IServiceClient<CuentaUsuarioViewModel> {
    bool Login(string username, string password);
    CuentaUsuarioViewModel? ObtenerPorUsername(string username);
}
