package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.Persona;

public interface PersonaDAO<M extends Persona> extends Persistible<M, Integer> {
    M buscarPorDni(String dni);
}
