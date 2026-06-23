using System;
using System.Threading;

namespace Ejercicio3;

public class Program
{
    private static Thread[] CrearProductores(int cantidad, ProductorID tarea)
    {
        Thread[] productores = new Thread[cantidad];
        for (int i = 0; i < cantidad; i++)
        {
            productores[i] = new Thread(tarea.Run);
            productores[i].Name = $"Productor-{i + 1}";
        }

        return productores;
    }

    private static Thread[] CrearConsumidores(int cantidad, ConsumidorID tarea)
    {
        Thread[] consumidores = new Thread[cantidad];
        for (int i = 0; i < cantidad; i++)
        {
            consumidores[i] = new Thread(tarea.Run);
            consumidores[i].Name = $"Consumidor-{i + 1}";
        }

        return consumidores;
    }

    private static void Iniciar(Thread[] hilos)
    {
        foreach (Thread hilo in hilos)
        {
            hilo.Start();
        }
    }

    private static void Esperar(Thread[] hilos)
    {
        foreach (Thread hilo in hilos)
        {
            try
            {
                hilo.Join();
            }
            catch (ThreadInterruptedException)
            {
                Thread.CurrentThread.Interrupt();
                Console.WriteLine("Hilo principal interrumpido");
                return;
            }
        }
    }

    public static void Main(string[] args)
    {
        ProductorID productor = new ProductorID();
        Thread[] productores = CrearProductores(3, productor);

        ConsumidorID consumidor = new ConsumidorID();
        Thread[] consumidores = CrearConsumidores(3, consumidor);

        Iniciar(productores);
        Iniciar(consumidores);
        Esperar(productores);
        Esperar(consumidores);
        Console.WriteLine();
        Console.WriteLine($"Identificador final: {Identificador.ObtenerId()}");
    }
}
