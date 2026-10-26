package pe.edu.pucp.softprog.modelo.seguridad;

import pe.edu.pucp.softprog.modelo.Registro;


public class CuentaUsuario extends Registro {
    private String userName;
    private String password ;

    public CuentaUsuario() {
    }

    public CuentaUsuario(final CuentaUsuario cuentaUsuario) {
        if  (cuentaUsuario == null) {
            throw new IllegalArgumentException("cuentaUsuario no puede ser nulo");
        }
        super(cuentaUsuario);
        setUserName(cuentaUsuario.getUserName());
        setPassword(cuentaUsuario.getPassword());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if (userName == null || userName.isEmpty()) {
            throw new IllegalArgumentException("userName no puede ser nulo o vacío");
        }

        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("password no puede ser nulo o vacío");
        }

        this.password = password;
    }
}
