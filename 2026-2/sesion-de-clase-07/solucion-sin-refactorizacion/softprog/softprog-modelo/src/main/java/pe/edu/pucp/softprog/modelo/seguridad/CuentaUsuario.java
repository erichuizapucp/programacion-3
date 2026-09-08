package pe.edu.pucp.softprog.modelo.seguridad;


public class CuentaUsuario {
    private int id;
    private boolean activo;
    private String userName;
    private String password ;

    public CuentaUsuario() {
    }

    public CuentaUsuario(final CuentaUsuario cuentaUsuario) {
        if  (cuentaUsuario == null) {
            throw new IllegalArgumentException("cuentaUsuario no puede ser nulo");
        }
        setId(cuentaUsuario.getId());
        setActivo(cuentaUsuario.isActivo());
        setUserName(cuentaUsuario.getUserName());
        setPassword(cuentaUsuario.getPassword());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("id no puede ser negativo");
        }
        this.id = id;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
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
