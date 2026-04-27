package pe.edu.pucp.softprog.bo;

import pe.edu.pucp.softprog.modelo.Estado;

import java.util.List;

public interface Gestionable<M> {
    List<M> listar();
    M obtener(int id);
    void eliminar(int id);
    void guardar(M modelo, Estado estado);
}
