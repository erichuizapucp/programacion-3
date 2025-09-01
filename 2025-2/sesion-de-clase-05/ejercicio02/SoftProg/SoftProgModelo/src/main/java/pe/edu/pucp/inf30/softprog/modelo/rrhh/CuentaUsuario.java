package pe.edu.pucp.inf30.softprog.modelo.rrhh;

import pe.edu.pucp.inf30.softprog.modelo.Registro;

/**
 *
 * @author eric
 */
public class CuentaUsuario extends Registro {
    private String userName;
    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
}
