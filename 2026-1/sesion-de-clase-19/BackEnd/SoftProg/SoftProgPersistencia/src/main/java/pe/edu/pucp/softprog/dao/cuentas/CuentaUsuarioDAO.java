package pe.edu.pucp.softprog.dao.cuentas;

import pe.edu.pucp.softprog.dao.Persistible;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

public interface CuentaUsuarioDAO extends Persistible<CuentaUsuario, Integer> {
    boolean login(String username, String password);
}

