namespace PUCP.Labs.Lab01.Preguntas;

using PUCP.Labs.Lab01.Interfaces;
using PUCP.Labs.Lab01.Examenes;

public abstract class Question : IPrintable, IAnswerable
{
    private readonly List<string> opciones;

    public int Codigo { get; }
    public string Prompt { get; }
    public IList<string> Opciones => opciones.AsReadOnly();

    public Question(int codigo, string prompt,
        List<string> opciones)
    {
        Codigo = codigo;
        Prompt = prompt;
        this.opciones = [.. opciones];
    }

    public virtual void DevolverDatos()
    {
        Console.WriteLine(Prompt);
        int index = 0;
        foreach (string opcion in opciones)
        {
            index++;
            Console.WriteLine($"{index}. {opcion}");
        }
    }

    public abstract void RecuperarRespuesta(AssessmentItem item);
}
