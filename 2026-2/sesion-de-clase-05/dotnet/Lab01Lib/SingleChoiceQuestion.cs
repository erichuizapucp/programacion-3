namespace PUCP.Labs.Lab01.Preguntas;

using PUCP.Labs.Lab01.Examenes;

public class SingleChoiceQuestion : Question
{
    public int Respuesta { get; }

    public SingleChoiceQuestion(
            int codigo,
            string prompt,
            List<string> opciones,
            int respuesta)
        : base(codigo, prompt, opciones)
    {
        Respuesta = respuesta;
    }

    public override void DevolverDatos()
    {
        base.DevolverDatos();
        Console.WriteLine("Ingrese su respuesta: ");
    }

    public override void RecuperarRespuesta(AssessmentItem item)
    {
        int respuestaAlumno = int.Parse(Console.ReadLine()!);
        item.EsCorrecto = Respuesta == respuestaAlumno;
    }
}
