package pe.edu.pucp.softprog.modelo;

import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import java.time.LocalDate;

public abstract class Persona extends Registro {
    private CuentaUsuario cuentaUsuario;
    private String dni;
    private String nombre;
    private String apellidoPaterno;
    private Genero genero;
    private LocalDate fechaNacimiento;

    public Persona() {
    }

    public Persona(final Persona persona) {
        if (persona == null) {
            throw new IllegalArgumentException("persona no puede ser nula");
        }
        super(persona);
        setCuentaUsuario(persona.getCuentaUsuario());
        setDni(persona.getDni());
        setNombre(persona.getNombre());
        setApellidoPaterno(persona.getApellidoPaterno());
        setGenero(persona.getGenero());
        setFechaNacimiento(persona.getFechaNacimiento());
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
}
