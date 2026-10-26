package pe.edu.pucp.softprog.bl;

import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

public interface CuentaUsuarioBL extends RegistroBL<CuentaUsuario, Integer> {
    CuentaUsuario findByUserName(String userName) throws BLException;
    boolean autenticar(String userName, String password) throws BLException;
    void cambiarPassword(int id, String passwordActual, String passwordNueva) throws BLException;
}
