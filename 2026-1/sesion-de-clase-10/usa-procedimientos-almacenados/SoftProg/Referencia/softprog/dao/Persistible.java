package pe.edu.pucp.inf30.softprog.dao;

import java.util.List;

/**
 *
 * @author eric
 */
public interface Persistible<T, I> {
    I crear(T modelo);
    boolean actualizar(T modelo);
    boolean eliminar(I id);
    T leer(I id);
    List<T> leerTodos();
}
