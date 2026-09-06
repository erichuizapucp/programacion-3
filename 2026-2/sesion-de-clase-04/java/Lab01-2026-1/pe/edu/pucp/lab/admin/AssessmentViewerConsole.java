package pe.edu.pucp.lab.admin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import pe.edu.pucp.lab.examenes.Assessment;
import pe.edu.pucp.lab.examenes.AssessmentItem;
import pe.edu.pucp.lab.preguntas.Question;

public class AssessmentViewerConsole {

    public static void show(Assessment assessment) {
        System.out.println("Inicio de examen ======");
        System.out.println(
                String.format("Duración: %d", assessment.getDuracion()));

        LocalDateTime fechaHora = assessment.getFechaHora();
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm");
        System.out.println(
                String.format("Fecha Hora Inicio: %s",
                        fechaHora.format(formatter)));

        List<AssessmentItem> items = assessment.getItems();
        int index = 0;
        int puntaje = 0;
        for (AssessmentItem item : items) {
            System.out.println(
                    String.format("Pregunta %d)", ++index));
            Question question = item.getPregunta();
            question.devolverDatos();
            question.recuperarRespuesta(item);
            boolean esCorrecto = item.getEsCorrecto();
            if (esCorrecto) {
                System.out.println("Puntaje Pregunta: " + item.getPuntaje());
                puntaje += item.getPuntaje();
            } else {
                System.out.println("Puntaje Pregunta: 0");
            }
        }
        assessment.setPuntaje(puntaje);
        System.out.println("Puntaje Final: " + puntaje);
    }
}
