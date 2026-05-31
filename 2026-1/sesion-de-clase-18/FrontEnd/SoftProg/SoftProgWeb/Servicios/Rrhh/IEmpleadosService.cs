using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public interface IEmpleadosService : IServiceBase<EmpleadoViewModel> {
    EmpleadoViewModel? BuscarPorDni(string dni);
}
