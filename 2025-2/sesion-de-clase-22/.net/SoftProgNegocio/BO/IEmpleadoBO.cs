using PUCP.SoftProg.Modelo.RRHH;

namespace PUCP.SoftProg.Negocio.BO {
    public interface IEmpleadoBO : IBaseBO<Empleado> {
        Empleado BuscarPorDni(string dni);
    }
}
