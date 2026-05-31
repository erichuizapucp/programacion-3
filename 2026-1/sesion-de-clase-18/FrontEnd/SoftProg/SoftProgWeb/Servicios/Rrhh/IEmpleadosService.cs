using SoftProgModelo.Modelos;
using SoftProgWeb.Servicios.Base;
using SoftProgWeb.ViewModels;

namespace SoftProgWeb.Servicios.Rrhh;

public interface IEmpleadosService : ICrudService<EmpleadoViewModel> {
    EmpleadoViewModel? BuscarPorDni(string dni);
}
