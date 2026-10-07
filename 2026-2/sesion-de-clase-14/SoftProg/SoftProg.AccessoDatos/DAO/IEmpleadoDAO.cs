using SoftProg.Modelo.RRHH;

namespace SoftProg.AccessoDatos.DAO {
    public interface IEmpleadoDAO : IPersonaDAO<Empleado> {
        List<Empleado> FilterByName(string nombre);
    }
}
