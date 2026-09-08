package pe.edu.pucp.softprog.modelo.ventas;

import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.time.LocalDate;

public class Cliente {
    private int id;
    private boolean activo;
    private CuentaUsuario cuentaUsuario;
    private String dni;
    private String nombre;
    private String apellidoPaterno;
    private Genero genero;
    private LocalDate fechaNacimiento;
    private CategoriaCliente categoria;
    private double lineaCredito;

    public Cliente() {
    }

    public Cliente(final Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El cliente no puede ser nulo");
        }
        setId(cliente.getId());
        setActivo(cliente.isActivo());
        setCuentaUsuario(cliente.getCuentaUsuario());
        setDni(cliente.getDni());
        setNombre(cliente.getNombre());
        setApellidoPaterno(cliente.getApellidoPaterno());
        setGenero(cliente.getGenero());
        setFechaNacimiento(cliente.getFechaNacimiento());
        setCategoria(cliente.getCategoria());
        setLineaCredito(cliente.getLineaCredito());
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

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaCliente categoria) {
        if (categoria == null) {
            throw new IllegalArgumentException("La categoría del cliente no puede ser nula");
        }
        this.categoria = categoria;
    }

    public double getLineaCredito() {
        return lineaCredito;
    }

    public void setLineaCredito(double lineaCredito) {
        if (lineaCredito < 0) {
            throw new IllegalArgumentException("La línea de crédito no puede ser negativa");
        }
        this.lineaCredito = lineaCredito;
    }
}
