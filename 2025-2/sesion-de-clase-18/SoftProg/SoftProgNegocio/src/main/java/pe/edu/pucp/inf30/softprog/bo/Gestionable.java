package pe.edu.pucp.inf30.softprog.bo;

import pe.edu.pucp.inf30.softprog.modelo.Estado;
import java.util.List;

/**
 *
 * @author eric
 */
public interface Gestionable<T> {
    List<T> listar();
    T obtener(int id);
    void eliminar(int id);
    void guardar(T modelo, Estado estado);
}
