using PUCP.Labs.Lab01.Preguntas;

namespace PUCP.Labs.Lab01.Examenes;

public class AssessmentItem
{
    public Question Pregunta { get; }
    public int Puntaje { get; }
    public bool EsCorrecto { get; set; }

    public AssessmentItem(Question pregunta, int puntaje)
    {
        Pregunta = pregunta;
        Puntaje = puntaje;
    }
}
