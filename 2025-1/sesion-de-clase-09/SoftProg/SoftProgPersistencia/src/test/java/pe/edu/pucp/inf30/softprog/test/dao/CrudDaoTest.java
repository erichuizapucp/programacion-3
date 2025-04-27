package pe.edu.pucp.inf30.softprog.test.dao;

public interface CrudDaoTest {
    void debeInsertar();
    void debeModificarSiIdExiste();
    void noDebeModificarSiIdNoExiste();
    void noDebeEliminarSiIdNoExiste();
    void debeEncontrarSiIdExiste();
    void noDebeEncontrarSiIdNoExiste();
    void debeListar();
    void debeEliminarSiIdExiste();
}
