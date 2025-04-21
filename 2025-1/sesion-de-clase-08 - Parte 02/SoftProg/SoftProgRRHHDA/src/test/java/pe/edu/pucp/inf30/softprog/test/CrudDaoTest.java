package pe.edu.pucp.inf30.softprog.test;

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
