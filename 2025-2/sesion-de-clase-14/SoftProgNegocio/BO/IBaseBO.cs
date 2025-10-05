using PUCP.SoftProg.Modelo;
using System.Collections.Generic;

namespace PUCP.SoftProg.Negocio.BO {
    public interface IBaseBO<T> {
        List<T> Listar();
        T Obtener(int id);
        void Eliminar(int id);
        void Guardar(T modelo, Estado estado);
    }
}
