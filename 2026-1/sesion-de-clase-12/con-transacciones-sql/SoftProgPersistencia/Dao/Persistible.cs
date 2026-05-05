namespace SoftProgPersistencia.Dao;

public interface Persistible<M, I>
{
    I Crear(M modelo);
    bool Actualizar(M modelo);
    bool Eliminar(I id);
    M? Leer(I id);
    List<M> LeerTodos();
}
