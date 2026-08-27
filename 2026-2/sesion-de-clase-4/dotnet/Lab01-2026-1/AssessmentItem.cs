namespace Lab01_2026_1;

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
