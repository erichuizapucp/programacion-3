package pe.edu.pucp.softprog.bo;

import pe.edu.pucp.softprog.modelo.Estado;

import java.util.List;

public interface Gestionable<T> {
    List<T> listar();
    T obtener(int id);
    void eliminar(int id);
    void guardar(T modelo, Estado estado);
}
