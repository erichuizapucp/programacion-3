package pe.edu.pucp.lab.preguntas;

import java.util.List;
import java.util.Scanner;

import pe.edu.pucp.lab.examenes.AssessmentItem;

public class SingleChoiceQuestion extends Question {

    private int respuesta;

    public SingleChoiceQuestion(
            int codigo,
            String prompt,
            List<String> opciones,
            int respuesta) {
        super(codigo, prompt, opciones);
        this.respuesta = respuesta;
    }

    public int getRespuesta() {
        return respuesta;
    }

    @Override
    public void devolverDatos() {
        super.devolverDatos();
        System.out.println("Ingrese su respuesta: ");
    }

    @Override
    public void recuperarRespuesta(AssessmentItem item) {
        Scanner scanner = new Scanner(System.in);
        int respuestaAlumno = scanner.nextInt();
        item.setEsCorrecto(respuesta == respuestaAlumno);
    }
}
