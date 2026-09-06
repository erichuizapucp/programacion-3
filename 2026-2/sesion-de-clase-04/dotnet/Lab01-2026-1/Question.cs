namespace Lab01_2026_1;

public abstract class Question : Printable, Answerable
{
    private readonly List<string> opciones;

    public int Codigo { get; }
    public string Prompt { get; }
    public IList<string> Opciones => opciones.AsReadOnly();

    public Question(int codigo, string prompt, List<string> opciones)
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
