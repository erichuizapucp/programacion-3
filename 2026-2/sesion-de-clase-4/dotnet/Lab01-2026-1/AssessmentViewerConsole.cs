namespace Lab01_2026_1;

public class AssessmentViewerConsole
{
    public static void Show(Assessment assessment)
    {
        Console.WriteLine("Inicio de examen ======");
        Console.WriteLine($"Duración: {assessment.Duracion}");

        DateTime fechaHora = assessment.FechaHora;
        Console.WriteLine($"Fecha Hora Inicio: {fechaHora:dd/MM/yyyy hh:mm}");

        IList<AssessmentItem> items = assessment.Items;
        int index = 0;
        int puntaje = 0;
        foreach (AssessmentItem item in items)
        {
            index++;
            Console.WriteLine($"Pregunta {index})");
            Question question = item.Pregunta;
            question.DevolverDatos();
            question.RecuperarRespuesta(item);
            bool esCorrecto = item.EsCorrecto;
            if (esCorrecto)
            {
                Console.WriteLine("Puntaje Pregunta: " + item.Puntaje);
                puntaje += item.Puntaje;
            }
            else
            {
                Console.WriteLine("Puntaje Pregunta: 0");
            }
        }
        assessment.Puntaje = puntaje;
        Console.WriteLine("Puntaje Final: " + puntaje);
    }
}
