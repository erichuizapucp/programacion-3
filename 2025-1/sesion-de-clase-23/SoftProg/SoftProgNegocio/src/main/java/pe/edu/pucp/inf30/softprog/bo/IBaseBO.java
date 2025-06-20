package pe.edu.pucp.inf30.softprog.bo;

import java.util.List;
import pe.edu.pucp.inf30.softprog.model.Estado;

/**
 *
 * @author eric
 * @param <T>
 */
public interface IBaseBO<T> {
    List<T> listar();
    T obtener(int id);
    void eliminar(int id);
    void guardar(T modelo, Estado estado);
}
