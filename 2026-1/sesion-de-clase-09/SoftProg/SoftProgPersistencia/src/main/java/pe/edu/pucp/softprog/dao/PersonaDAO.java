package pe.edu.pucp.softprog.dao;

public interface PersonaDAO<M> extends Persistible<M, Integer> {
    M buscarPorDni(String dni);
}
