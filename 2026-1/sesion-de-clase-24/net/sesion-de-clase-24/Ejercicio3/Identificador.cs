using System;
using System.Threading;

namespace Ejercicio3;

public static class Identificador
{
    private static readonly object lockObject = new object();

    public static int Id { get; set; }

    public static void Incrementar()
    {
        lock (lockObject)
        {
            Id++;
            Console.WriteLine($"{Id}: producido por -> {Thread.CurrentThread.Name}");
        }
    }

    public static int ObtenerId()
    {
        lock (lockObject)
        {
            return Id;
        }
    }

    public static void ImprimirIdActualConsumido(string nombreConsumidor)
    {
        lock (lockObject)
        {
            Console.WriteLine($"{nombreConsumidor} leyo identificador actual: {Id}");
        }
    }
}
