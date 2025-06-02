using System.Collections.Generic;
using System.Data.Common;

namespace PUCP.Edu.Pe.SoftProg.Persistencia.DAO {
    public interface ICrud<T> {
        int Insertar(T modelo);
        int Insertar(T modelo, DbConnection conexion, DbTransaction transaction);
        bool Modificar(T modelo);
        bool Eliminar(int id);
        T Buscar(int id);
        List<T> Listar();
    }
}
