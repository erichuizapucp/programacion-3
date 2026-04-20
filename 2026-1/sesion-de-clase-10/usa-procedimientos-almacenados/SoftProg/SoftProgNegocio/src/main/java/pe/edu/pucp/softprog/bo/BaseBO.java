package pe.edu.pucp.softprog.bo;

import pe.edu.pucp.softprog.modelo.Estado;

import java.util.Objects;

public abstract class BaseBO {
    protected void validarIdPositivo(int id, String nombreCampo) {
        if (id <= 0) {
            throw new IllegalArgumentException("El " + nombreCampo + " debe ser mayor a 0");
        }
    }

    protected void validarEstado(Estado estado) {
        Objects.requireNonNull(estado, "El estado es obligatorio");
    }

    protected void validarTextoObligatorio(String valor, String nombreCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("El " + nombreCampo + " es obligatorio");
        }
    }
}

