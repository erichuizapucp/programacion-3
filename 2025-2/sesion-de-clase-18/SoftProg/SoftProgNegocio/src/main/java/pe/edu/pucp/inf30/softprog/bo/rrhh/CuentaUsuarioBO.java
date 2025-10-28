package pe.edu.pucp.inf30.softprog.bo.rrhh;

import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.bo.Gestionable;

/**
 *
 * @author eric
 */
public interface CuentaUsuarioBO extends Gestionable<CuentaUsuario> {
    boolean login(String userName, String password);
}
