using System;
using System.Threading;

namespace Ejercicio4;

public class CuentaBancaria
{
    private readonly object lockObject = new object();

    public double Saldo { get; set; } = 500.00;

    public void Retirar(string nombreCliente, double monto)
    {
        lock (lockObject)
        {
            while (Saldo < monto)
            {
                Console.WriteLine($"{nombreCliente} quiere retirar {monto} pero no hay sificiente, esperando...");
                Monitor.Wait(lockObject);
            }

            Console.WriteLine($"El cliente {nombreCliente} va a retirar {monto}");
            Saldo -= monto;
            Console.WriteLine($"{nombreCliente} completo el retiro, queda {Saldo}");
        }
    }

    public void Depositar(string nombreCliente, double monto)
    {
        lock (lockObject)
        {
            Console.WriteLine($"El cliente {nombreCliente} deposito {monto}");
            Saldo += monto;
            Console.WriteLine($"Saldo despues del deposito: {Saldo}");
            Monitor.PulseAll(lockObject);
        }
    }
}
