using System.Collections.Generic;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH {
    public interface IEmpleadoDAO : ICrud<Empleado> {
        List<Empleado> BuscarPorDni(string dni);
    }
}
