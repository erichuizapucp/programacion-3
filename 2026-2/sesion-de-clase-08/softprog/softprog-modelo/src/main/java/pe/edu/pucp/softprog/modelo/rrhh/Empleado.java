package pe.edu.pucp.softprog.modelo.rrhh;

import pe.edu.pucp.softprog.modelo.Persona;

public class Empleado extends Persona {
    private Area area;
    private Cargo cargo;
    private double sueldo;

    public Empleado() {
    }

    public Empleado(final Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("empleado no puede ser nulo");
        }
        super(empleado);
        setArea(empleado.getArea());
        setCargo(empleado.getCargo());
        setSueldo(empleado.getSueldo());
    }

    public Area getArea() {
        return new Area(area);
    }

    public void setArea(Area area) {
        if (area == null) {
            throw new IllegalArgumentException("area no puede ser nulo");
        }
        this.area = new Area(area);
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        if (cargo == null) {
            throw new IllegalArgumentException("cargo no puede ser nulo");
        }
        this.cargo = cargo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        if (sueldo < 0) {
            throw new IllegalArgumentException("sueldo no puede ser negativo");
        }
        this.sueldo = sueldo;
    }
}
