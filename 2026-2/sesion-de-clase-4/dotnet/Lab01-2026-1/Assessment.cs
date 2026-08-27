namespace Lab01_2026_1;

public class Assessment
{
    private readonly List<AssessmentItem> items;

    public int Duracion { get; }
    public DateTime FechaHora { get; }
    public IList<AssessmentItem> Items => items.AsReadOnly();
    public int Puntaje { get; set; }

    public Assessment(
            int duracion,
            DateTime fechaHora,
            List<AssessmentItem> items)
    {
        Duracion = duracion;
        FechaHora = fechaHora;
        this.items = [.. items];
    }
}
