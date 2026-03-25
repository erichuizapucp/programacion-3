public class Alumno : Persona, IGrabable
{
    public Alumno() : base()
    {
        Codigo = 0;
        CRAEST = 0.00;
    }

    public Alumno(int codigo, string nombre, double craest) : base(nombre)
    {
        Codigo = codigo;
        Nombre = nombre;
        CRAEST = craest;
    }

    public int Codigo { get; set; }
    public double CRAEST { get; set; }

    public override void Imprimir()
    {
        base.Imprimir();
        Console.WriteLine($"Código: {Codigo}");
        Console.WriteLine($"CRAEST: {CRAEST}");
    }

    public override void Grabar()
    {
        base.Grabar();
        Console.WriteLine("Grabando información del alumno...");
    }
}