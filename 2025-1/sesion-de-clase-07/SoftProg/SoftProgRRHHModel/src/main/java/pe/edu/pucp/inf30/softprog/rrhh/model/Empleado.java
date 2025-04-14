package pe.edu.pucp.inf30.softprog.rrhh.model;

/**
 *
 * @author eric
 */
public class Empleado extends Persona {
    private String cargo;
    private double sueldo;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }
}