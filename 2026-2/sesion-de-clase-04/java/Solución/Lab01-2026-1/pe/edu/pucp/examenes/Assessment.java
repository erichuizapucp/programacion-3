package pe.edu.pucp.examenes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Assessment {

    private int duracion;
    private LocalDateTime fechaHora;
    private List<AssessmentItem> items;
    private int puntaje;

    public Assessment(int duracion,
            LocalDateTime fechaHora,
            List<AssessmentItem> items) {

        this.duracion = duracion;
        this.fechaHora = fechaHora;
        this.items = new ArrayList<>(items);
    }

    public int getDuracion() {
        return duracion;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public List<AssessmentItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}
