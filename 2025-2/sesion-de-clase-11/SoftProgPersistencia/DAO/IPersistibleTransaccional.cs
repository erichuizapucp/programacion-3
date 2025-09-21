using System.Data.Common;

namespace PUCP.SoftProg.Persistencia.DAO {
    public interface IPersistibleTransaccional<T, I> : IPersistible<T, I> {
        I Crear(T modelo, DbTransaction transaccion);
        bool Actualizar(T modelo, DbTransaction transaccion);
        bool Eliminar(I id, DbTransaction transaccion);
    }
}
