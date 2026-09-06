package pe.edu.pucp.lab.examenes;

import pe.edu.pucp.lab.preguntas.Question;

public class AssessmentItem {

    private Question pregunta;
    private int puntaje;
    private boolean esCorrecto;

    public AssessmentItem(Question pregunta, int puntaje) {
        this.pregunta = pregunta;
        this.puntaje = puntaje;
    }

    public void setEsCorrecto(boolean esCorrecto) {
        this.esCorrecto = esCorrecto;
    }

    public Question getPregunta() {
        return pregunta;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public boolean getEsCorrecto() {
        return esCorrecto;
    }
}
