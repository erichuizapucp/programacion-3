using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BO {
    public interface IEmpleadoBO {
        List<Empleado> Listar();
        Empleado Obtener(int id);
        void Eliminar(int id);
        void Guardar(Empleado area, Estado estado);
    }
}
