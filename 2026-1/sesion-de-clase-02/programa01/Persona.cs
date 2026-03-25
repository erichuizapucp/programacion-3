public class Persona : Registro, IGrabable
{
    public Persona()
    {
        Nombre = "[NOMBRE]";
    }

    public Persona(string nombre)
    {
        Nombre = nombre;
    }

    public string Nombre { get; set; }

    public override void Imprimir()
    {
        Console.WriteLine($"Nombre: {Nombre}");
    }

    public virtual void Grabar()
    {
        Console.WriteLine("Grabando información de la persona...");
    }
}