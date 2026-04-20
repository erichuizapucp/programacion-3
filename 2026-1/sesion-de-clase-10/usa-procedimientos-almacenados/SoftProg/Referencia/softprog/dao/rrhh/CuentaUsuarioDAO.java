package pe.edu.pucp.inf30.softprog.dao.rrhh;

import pe.edu.pucp.inf30.softprog.dao.PersistibleTransaccional;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
public interface CuentaUsuarioDAO extends PersistibleTransaccional<CuentaUsuario, Integer>  {
    boolean login(String username, String password);
}
