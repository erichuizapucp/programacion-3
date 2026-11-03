using System;
using System.Threading;

namespace Ejercicio2;

public class Program
{
    private static void ImprimirEstado(string mensaje, Thread hilo)
    {
        Console.WriteLine($"{mensaje}: {hilo.ThreadState}");
    }

    private static void EsperarFinalizacion(Thread hilo)
    {
        try
        {
            hilo.Join();
        }
        catch (ThreadInterruptedException)
        {
            Thread.CurrentThread.Interrupt();
            Console.WriteLine("Hilo principal interrumpido");
        }
    }

    public static void Main(string[] args)
    {
        HiloConPausa hiloConPausa = new HiloConPausa();
        Thread hilo = new Thread(hiloConPausa.Run);
        hilo.Name = "Hilo 1";

        ImprimirEstado("Estado inicial", hilo);
        hilo.Start();
        ImprimirEstado("Estado luego de start", hilo);
        EsperarFinalizacion(hilo);
        ImprimirEstado("Estado final", hilo);
        Console.WriteLine("Hilo Principal");
    }
}
