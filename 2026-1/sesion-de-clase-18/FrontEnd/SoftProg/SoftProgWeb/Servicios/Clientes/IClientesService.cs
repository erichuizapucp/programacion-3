using SoftProgModelo.Modelos;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Clientes;

public interface IClientesService : ICrudService<ClienteViewModel> {
    ClienteViewModel? BuscarPorDni(string dni);
    ClienteViewModel? BuscarPorCuenta(string cuenta);
}
