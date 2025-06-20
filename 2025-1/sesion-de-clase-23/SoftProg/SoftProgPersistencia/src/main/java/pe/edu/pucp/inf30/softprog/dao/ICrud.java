package pe.edu.pucp.inf30.softprog.dao;

import java.sql.Connection;
import java.util.List;

/**
 *
 * @author eric
 * @param <T>
 */
public interface ICrud<T> {
    int insertar(T modelo);
    int insertar(T modelo, Connection conexion);
    boolean modificar(T modelo);
    boolean modificar(T modelo, Connection conexion);
    boolean eliminar(int id);
    boolean eliminar(int id, Connection conexion);
    T buscar(int id);
    List<T> listar();
}