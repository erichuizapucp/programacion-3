using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Clientes;

public interface IClientesServiceClient : IServiceClient<ClienteViewModel> {
    ClienteViewModel? BuscarPorDni(string dni);
    ClienteViewModel? BuscarPorCuenta(string cuenta);
}
