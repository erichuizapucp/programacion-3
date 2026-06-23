using System.Threading;

namespace Ejercicio4;

public class Deposito
{
    public CuentaBancaria Cuenta { get; set; }

    public double Monto { get; set; }

    public Deposito(CuentaBancaria cuenta, double monto)
    {
        Cuenta = cuenta;
        Monto = monto;
    }

    public void Run()
    {
        string nombreCliente = Thread.CurrentThread.Name ?? "Sin nombre";
        Cuenta.Depositar(nombreCliente, Monto);
    }
}
