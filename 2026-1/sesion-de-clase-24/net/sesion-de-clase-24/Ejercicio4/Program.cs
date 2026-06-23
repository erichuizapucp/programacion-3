using System.Threading;

namespace Ejercicio4;

public class Program
{
    public static void Main(string[] args)
    {
        CuentaBancaria cuenta = new CuentaBancaria();

        Retiro retirar100 = new Retiro(cuenta, 100.00);
        Retiro retirar70 = new Retiro(cuenta, 70.00);
        Retiro retirar60 = new Retiro(cuenta, 60.00);
        Retiro retirar30 = new Retiro(cuenta, 30.00);

        Deposito depositar100 = new Deposito(cuenta, 100);
        Deposito depositar50 = new Deposito(cuenta, 50);
        Deposito depositar300 = new Deposito(cuenta, 300);

        Thread t1 = new Thread(retirar100.Run) { Name = "Gerente General" };
        Thread t2 = new Thread(retirar70.Run) { Name = "Director RRHH" };
        Thread t3 = new Thread(retirar70.Run) { Name = "Director Ventas" };
        Thread t4 = new Thread(retirar30.Run) { Name = "Asistente Adminitrativo 1" };
        Thread t5 = new Thread(retirar60.Run) { Name = "Director Almacen" };
        Thread t6 = new Thread(retirar100.Run) { Name = "Asistente Administrativo 2" };
        Thread t7 = new Thread(retirar100.Run) { Name = "Director Sistemas" };
        Thread t8 = new Thread(retirar100.Run) { Name = "Asistente Administrativo 3" };
        Thread t9 = new Thread(retirar100.Run) { Name = "Asistente Administrativo 4" };
        Thread t10 = new Thread(retirar100.Run) { Name = "Asistente Administrativo 5" };
        Thread t11 = new Thread(retirar100.Run) { Name = "Asistente Administrativo 6" };
        Thread t12 = new Thread(retirar100.Run) { Name = "Asistente Administrativo 7" };
        Thread t13 = new Thread(retirar100.Run) { Name = "Asistente Administrativo 8" };

        Thread t14 = new Thread(depositar50.Run) { Name = "Cliente 1" };
        Thread t15 = new Thread(depositar100.Run) { Name = "Cliente 2" };
        Thread t16 = new Thread(depositar300.Run) { Name = "Cliente 3" };
        Thread t17 = new Thread(depositar100.Run) { Name = "Cliente 4" };
        Thread t18 = new Thread(depositar50.Run) { Name = "Cliente 5" };
        Thread t19 = new Thread(depositar50.Run) { Name = "Cliente 6" };
        Thread t20 = new Thread(depositar50.Run) { Name = "Cliente 7" };

        t1.Start();
        t2.Start();
        t3.Start();
        t4.Start();
        t5.Start();
        t6.Start();
        t7.Start();
        t8.Start();
        t9.Start();
        t10.Start();
        t11.Start();
        t12.Start();
        t13.Start();

        try
        {
            Thread.Sleep(10000);
        }
        catch (ThreadInterruptedException)
        {
            Thread.CurrentThread.Interrupt();
        }

        t14.Start();
        t15.Start();
        t16.Start();
        t17.Start();
        t18.Start();
        t19.Start();
        t20.Start();
    }
}
