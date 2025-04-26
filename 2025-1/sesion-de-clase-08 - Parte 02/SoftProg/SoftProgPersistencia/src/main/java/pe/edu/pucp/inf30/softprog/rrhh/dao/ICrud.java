package pe.edu.pucp.inf30.softprog.rrhh.dao;

import java.util.List;

/**
 *
 * @author eric
 * @param <T>
 */
public interface ICrud<T> {
    int insertar(T modelo);
    boolean modificar(T modelo);
    boolean eliminar(int id);
    T buscar(int id);
    List<T> listar();
}