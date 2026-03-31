using Coberturas;
using Seguros;

public class Program
{
    public static void Main(string[] args)
    {
        Vehiculo vehiculo = new()
        {
            Antiguedad = 2,
            Marca = Marca.Toyota
        };

        Console.WriteLine("===== SEGUROS PREDEFINIDOS =====");

        ICotizable seguro = new SeguroBasico(vehiculo);
        Console.WriteLine(seguro);

        seguro = new SeguroBronce(vehiculo);
        Console.WriteLine(seguro);

        seguro = new SeguroPlata(vehiculo);
        Console.WriteLine(seguro);

        seguro = new SeguroOro(vehiculo);
        Console.WriteLine(seguro);

        /* Implementación utilizando el Patrón Decorador 
            * para extender funcionalidades de manera flexible */
        Console.WriteLine("\n===== SEGUROS DECORADOS (Patrón Decorador) =====");

        seguro = new SeguroBasico(vehiculo);
        Console.WriteLine(seguro);

        seguro = new SeguroBasico(vehiculo)
            .ConCoberturaContraRobos();
        Console.WriteLine(seguro);

        seguro = new SeguroBasico(vehiculo)
            .ConCoberturaContraRobos()
            .ConAsistenciaVial();
        Console.WriteLine(seguro);

        seguro = new SeguroBasico(vehiculo)
            .ConCoberturaContraRobos()
            .ConAsistenciaVial()
            .ConCoberturaContraDesastresNaturales();
        Console.WriteLine(seguro);

        seguro = new SeguroBasico(vehiculo)
            .ConCoberturaContraRobos()
            .ConCoberturaContraDesastresNaturales();
        Console.WriteLine(seguro);
    }
}
