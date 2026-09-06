namespace PUCP.Labs.Lab01.Preguntas;

using PUCP.Labs.Lab01.Examenes;

public class MultipleChoiceQuestion : Question
{
    private readonly List<int> respuestas;

    public MultipleChoiceQuestion(
            int codigo,
            string prompt,
            List<string> opciones,
            List<int> respuestas)
        : base(codigo, prompt, opciones)
    {
        this.respuestas = [.. respuestas];
    }

    public override void DevolverDatos()
    {
        base.DevolverDatos();
        Console.WriteLine("Seleccione las opciones de su respuesta: ");
    }

    public override void RecuperarRespuesta(AssessmentItem item)
    {
        string respuestaAlumno = Console.ReadLine()!;
        string[] resps = respuestaAlumno.Split(',');
        List<int> respsList = new List<int>();
        foreach (string r in resps)
        {
            respsList.Add(int.Parse(r));
        }

        item.EsCorrecto = respuestas.SequenceEqual(respsList);
    }
}
