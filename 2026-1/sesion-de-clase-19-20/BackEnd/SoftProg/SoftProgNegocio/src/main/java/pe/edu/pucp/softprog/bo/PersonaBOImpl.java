package pe.edu.pucp.softprog.bo;

import pe.edu.pucp.softprog.modelo.Persona;

import java.util.Objects;

public abstract class PersonaBOImpl<M extends Persona> extends BaseBO implements PersonaBO<M> {
    protected void validarPersonaBasica(M modelo, String nombreEntidad) {
        Objects.requireNonNull(modelo, "El " + nombreEntidad + " es obligatorio");
        validarTextoObligatorio(modelo.getDni(), "dni");
        validarTextoObligatorio(modelo.getNombre(), "nombre");
        validarTextoObligatorio(modelo.getApellidoPaterno(), "apellido paterno");
        Objects.requireNonNull(modelo.getGenero(), "El genero es obligatorio");
        Objects.requireNonNull(modelo.getFechaNacimiento(), "La fecha de nacimiento es obligatoria");
    }
}

