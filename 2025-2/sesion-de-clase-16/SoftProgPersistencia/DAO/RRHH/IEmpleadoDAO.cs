using System.Collections.Generic;
using PUCP.SoftProg.Modelo.RRHH;

namespace PUCP.SoftProg.Persistencia.DAO.RRHH {
    public interface IEmpleadoDAO : IPersistible<Empleado, int> {
        Empleado BuscarPorDni(string dni);
    }
}
