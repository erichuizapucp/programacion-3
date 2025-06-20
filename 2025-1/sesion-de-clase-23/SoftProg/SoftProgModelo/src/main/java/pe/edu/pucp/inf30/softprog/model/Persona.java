package pe.edu.pucp.inf30.softprog.model;

import jakarta.json.bind.annotation.JsonbDateFormat;
import java.util.Date;

public class Persona extends ModeloBase {
    private String dni;
    private String nombre;
    private String apellidoPaterno;
    private char genero;
    
    @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    private Date fechaNacimiento;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}