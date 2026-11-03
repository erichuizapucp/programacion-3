using System.Threading;

namespace Ejercicio4;

public class Retiro
{
    public CuentaBancaria Cuenta { get; set; }

    public double Monto { get; set; }

    public Retiro(CuentaBancaria cuenta, double monto)
    {
        Cuenta = cuenta;
        Monto = monto;
    }

    public void Run()
    {
        string nombreCliente = Thread.CurrentThread.Name ?? "Sin nombre";
        Cuenta.Retirar(nombreCliente, Monto);
    }
}
