using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public interface IEmpleadosServiceClient : IServiceClient<EmpleadoViewModel> {
    EmpleadoViewModel? BuscarPorDni(string dni);
}
