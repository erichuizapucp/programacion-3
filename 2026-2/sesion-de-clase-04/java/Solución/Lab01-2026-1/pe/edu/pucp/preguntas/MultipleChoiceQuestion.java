package pe.edu.pucp.preguntas;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pe.edu.pucp.examenes.AssessmentItem;

public class MultipleChoiceQuestion extends Question {

    private List<Integer> respuestas;

    public MultipleChoiceQuestion(
            int codigo,
            String prompt,
            List<String> opciones,
            List<Integer> respuestas) {
        super(codigo, prompt, opciones);
        this.respuestas = new ArrayList<>(respuestas);
    }

    @Override
    public void devolverDatos() {
        super.devolverDatos();
        System.out.println("Seleccione las opciones de su respuesta: ");
    }

    @Override
    public void recuperarRespuesta(AssessmentItem item) {
        Scanner scanner = new Scanner(System.in);
        String respuestaAlumno = scanner.next();
        String[] resps = respuestaAlumno.split(",");
        List<Integer> respsList = new ArrayList<>();
        for (String r : resps) {
            respsList.add(Integer.parseInt(r));
        }

        item.setEsCorrecto(respuestas.equals(respsList));
    }
}
