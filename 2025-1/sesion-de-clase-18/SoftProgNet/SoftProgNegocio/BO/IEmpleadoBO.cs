using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BO {
    public interface IEmpleadoBO : IBaseBO<Empleado> {
        List<Empleado> BuscarPorDni(string dni);
    }
}
