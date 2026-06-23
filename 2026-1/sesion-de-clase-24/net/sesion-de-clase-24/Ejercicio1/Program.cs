using System;
using System.Threading;

namespace Ejercicio1;

public class Program
{
    public static void Main(string[] args)
    {
        HiloDemostrativo hiloDemostrativo = new HiloDemostrativo();
        Thread hilo = new Thread(hiloDemostrativo.Run);
        hilo.Name = "Hilo 1";
        hilo.Start();
        Console.WriteLine("Hilo Principal");
    }
}
