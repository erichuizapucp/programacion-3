package pe.edu.pucp.inf30.softprog.model.rrhh;

import pe.edu.pucp.inf30.softprog.model.ModeloBase;

public class CuentaUsuario extends ModeloBase {
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