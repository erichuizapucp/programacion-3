package pe.edu.pucp.softprog.bo.cuentas;

import pe.edu.pucp.softprog.bo.Gestionable;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

public interface CuentaUsuarioBO extends Gestionable<CuentaUsuario> {
	boolean login(String username, String password);
}
