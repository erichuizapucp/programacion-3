namespace PUCP.Labs.Lab01.Admin;

using PUCP.Labs.Lab01.Preguntas;

public class QuestionManager
{
    private readonly List<Question> preguntas = new List<Question>();

    public IList<Question> Preguntas => preguntas.AsReadOnly();

    public void Add(Question question)
    {
        preguntas.Add(question);
    }

    public List<Question> SelectRandomN(int cantidad)
    {
        List<Question> copia = [.. preguntas];
        Random random = new();
        int n = copia.Count;
        while (n > 1)
        {
            n--;
            int k = random.Next(n + 1);
            (copia[k], copia[n]) = (copia[n], copia[k]);
        }
        return copia.GetRange(0, Math.Min(cantidad, copia.Count));
    }
}
