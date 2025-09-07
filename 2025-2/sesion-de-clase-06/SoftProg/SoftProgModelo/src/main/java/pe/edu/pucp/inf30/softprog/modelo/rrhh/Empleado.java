package pe.edu.pucp.inf30.softprog.modelo.rrhh;

import pe.edu.pucp.inf30.softprog.modelo.Persona;

/**
 *
 * @author eric
 */
public class Empleado extends Persona {
    private Cargo cargo;
    private double sueldo;
    private Area area;
    private CuentaUsuario cuentaUsuario;

    public CuentaUsuario getCuentaUsuario() {
        return cuentaUsuario;
    }

    public void setCuentaUsuario(CuentaUsuario cuentaUsuario) {
        this.cuentaUsuario = cuentaUsuario;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }
    
    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
}
