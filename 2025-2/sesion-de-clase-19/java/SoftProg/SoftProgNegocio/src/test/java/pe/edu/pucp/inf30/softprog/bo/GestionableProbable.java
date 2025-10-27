package pe.edu.pucp.inf30.softprog.bo;

/**
 *
 * @author eric
 */
public interface GestionableProbable {
    void debeListar();
    void debeObtenerSiIdExiste();
    void noDebeObtenerSiIdNoExiste();
    void debeGuardarNuevo();
    void debeGuardarModificado();
    void debeEliminarSiIdExiste();
    void noDebeEliminarSiIdNoExiste();
    void debeHacerRollbackSiErrorEnGuardar();
    void debeHacerRollbackSiErrorEnEliminar();
}
