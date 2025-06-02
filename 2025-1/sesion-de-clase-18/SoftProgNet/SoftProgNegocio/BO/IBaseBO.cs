using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PUCP.Edu.Pe.SoftProg.Negocio.BO {
    public interface IBaseBO<T> {
        List<T> Listar();
        T Obtener(int id);
        void Eliminar(int id);
        void Guardar(T modelo, Estado estado);
    }
}
