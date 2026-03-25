public class Program
{
    public static void Main(string[] args)
    {
        Persona persona = new Alumno(12345, "Juan Perez", 60.5);
        persona.Imprimir();
        persona.Grabar();

        Console.WriteLine();

        IGrabable grabable = new Alumno(12345, "Juan Perez", 60.5);
        grabable.Grabar();
    }
}