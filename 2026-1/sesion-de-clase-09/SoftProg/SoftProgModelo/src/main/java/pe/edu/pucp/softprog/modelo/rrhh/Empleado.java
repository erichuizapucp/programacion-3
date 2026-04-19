package pe.edu.pucp.softprog.modelo.rrhh;

import pe.edu.pucp.softprog.modelo.Persona;

public class Empleado extends Persona {
    private Cargo cargo;
    private double sueldo;
    private Area area;

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
    
    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + getId() +
                ", activo=" + isActivo() +
                ", dni='" + getDni() + '\'' +
                ", nombre='" + getNombre() + '\'' +
                ", apellidoPaterno='" + getApellidoPaterno() + '\'' +
                ", genero=" + getGenero() +
                ", fechaNacimiento=" + getFechaNacimiento() +
                ", cargo=" + cargo +
                ", sueldo=" + sueldo +
                ", area=" + (area != null ? area.getNombre() : null) +
                ", cuentaUsuario=" + getCuentaUsuario() +
                '}';
    }
}
