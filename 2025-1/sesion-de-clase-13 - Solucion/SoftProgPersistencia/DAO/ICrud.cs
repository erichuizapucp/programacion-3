using System.Collections.Generic;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAO {
    public interface ICrud<T> {
        int Insertar(T modelo);
        bool Modificar(T modelo);
        bool Eliminar(int id);
        T Buscar(int id);
        List<T> Listar();
    }
}
