package pe.edu.pucp.inf30.softprog.test.dao;

public interface PersistibleProbable {
    void debeCrear();
    void debeActualizarSiIdExiste();
    void noDebeActualizarSiIdNoExiste();
    void noDebeEliminarSiIdNoExiste();
    void debeLeerSiIdExiste();
    void noDebeLeerSiIdNoExiste();
    void debeLeerTodos();
    void debeEliminarSiIdExiste();
}
