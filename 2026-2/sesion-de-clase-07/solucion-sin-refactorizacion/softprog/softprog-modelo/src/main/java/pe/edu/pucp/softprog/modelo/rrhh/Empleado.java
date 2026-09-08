package pe.edu.pucp.softprog.modelo.rrhh;

import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.time.LocalDate;

public class Empleado {
    private int id;
    private boolean activo;
    private CuentaUsuario cuentaUsuario;
    private String dni;
    private String nombre;
    private String apellidoPaterno;
    private Genero genero;
    private LocalDate fechaNacimiento;
    private Area area;
    private Cargo cargo;
    private double sueldo;

    public Empleado() {
    }

    public Empleado(final Empleado empleado) {
        if (empleado == null) {
            throw new IllegalArgumentException("empleado no puede ser nulo");
        }
        setId(empleado.getId());
        setActivo(empleado.isActivo());
        setCuentaUsuario(empleado.getCuentaUsuario());
        setDni(empleado.getDni());
        setNombre(empleado.getNombre());
        setApellidoPaterno(empleado.getApellidoPaterno());
        setGenero(empleado.getGenero());
        setFechaNacimiento(empleado.getFechaNacimiento());
        setArea(empleado.getArea());
        setCargo(empleado.getCargo());
        setSueldo(empleado.getSueldo());
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

    public CuentaUsuario getCuentaUsuario() {
        return new CuentaUsuario(cuentaUsuario);
    }

    public void setCuentaUsuario(CuentaUsuario cuentaUsuario) {
        if (cuentaUsuario == null) {
            throw new IllegalArgumentException("cuentaUsuario no puede ser nulo");
        }
        this.cuentaUsuario = new CuentaUsuario(cuentaUsuario);
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if  (dni == null) {
            throw new IllegalArgumentException("dni no puede ser nulo");
        }
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("nombre no puede ser nulo o vacío");
        }
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        if (apellidoPaterno == null || apellidoPaterno.isEmpty()) {
            throw new IllegalArgumentException("apellidoPaterno no puede ser nulo o vacío");
        }
        this.apellidoPaterno = apellidoPaterno;
    }

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        if (genero == null) {
            throw new IllegalArgumentException("genero no puede ser nulo");
        }
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("fechaNacimiento no puede ser nulo");
        }
        this.fechaNacimiento = fechaNacimiento;
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
