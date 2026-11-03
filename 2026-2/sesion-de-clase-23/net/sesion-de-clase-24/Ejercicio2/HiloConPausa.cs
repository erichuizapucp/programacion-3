using System;
using System.Threading;

namespace Ejercicio2;

public class HiloConPausa
{
    private const int PausaMillis = 5000;

    public void Run()
    {
        try
        {
            Thread.Sleep(PausaMillis);
        }
        catch (ThreadInterruptedException)
        {
            Thread.CurrentThread.Interrupt();
            return;
        }

        Console.WriteLine($"Hilo '{Thread.CurrentThread.Name}'");
    }
}
