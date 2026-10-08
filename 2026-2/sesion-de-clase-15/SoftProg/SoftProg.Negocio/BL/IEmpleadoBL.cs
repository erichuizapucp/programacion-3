using SoftProg.Modelo.RRHH;

namespace SoftProg.Negocio.BL {
    public interface IEmpleadoBL : IRegistroBL<Empleado, int> {
        List<Empleado> FilterByName(string nombre);
    }
}
