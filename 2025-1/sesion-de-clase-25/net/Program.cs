using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Threading;

namespace sesion_de_clase_24_net
{
    internal class Program
    {
        static void Main(string[] args)
        {
            //Thread t1 = new Thread(() => {
            //    Console.WriteLine(Thread.CurrentThread.Name + ", en ejecucion.");
            //});
            //t1.Name = "Hilo 1";
            //t1.Start();

            //ThreadStart productor = () => {
            //    for (int i = 0; i < 10; i++) {
            //        Identificador.Incrementar();
            //    }
            //};

            //Thread p1 = new Thread(productor);
            //Thread p2 = new Thread(productor);

            //p1.Start();
            //p2.Start();

            CuentaBancaria cuenta = new CuentaBancaria(500.00);
            ParameterizedThreadStart retirar = (object montoObj) =>
            {
                double monto = (double)montoObj;
                cuenta.Retirar(Thread.CurrentThread.Name, monto);
            };

            ParameterizedThreadStart depositar = (object montoObj) => { 
                double monto = (double)(montoObj);
                cuenta.Depositar(Thread.CurrentThread.Name, monto);
            };

            Thread t1 = new Thread(retirar)
            {
                Name = "Gerente General"
            };
            Thread t2 = new Thread(retirar)
            {
                Name = "Director RRHH"
            };
            Thread t3 = new Thread(retirar)
            {
                Name = "Director Ventas"
            };
            Thread t4 = new Thread(retirar)
            {
                Name = "Asistente Adminitrativo 1"
            };
            Thread t5 = new Thread(retirar)
            {
                Name = "Director Almacen"
            };
            Thread t6 = new Thread(retirar)
            {
                Name = "Asistente Administrativo 2"
            };
            Thread t7 = new Thread(retirar)
            {
                Name = "Director Sistemas"
            };
            Thread t8 = new Thread(retirar)
            {
                Name = "Asistente Administrativo 3"
            };
            Thread t9 = new Thread(retirar)
            {
                Name = "Asistente Administrativo 4"
            };
            Thread t10 = new Thread(retirar)
            {
                Name = "Asistente Administrativo 5"
            };
            Thread t11 = new Thread(retirar)
            {
                Name = "Asistente Administrativo 6"
            };
            Thread t12 = new Thread(retirar)
            {
                Name = "Asistente Administrativo 7"
            };
            Thread t13 = new Thread(retirar)
            {
                Name = "Asistente Administrativo 8"
            };

            Thread t14 = new Thread(depositar) 
            {
                Name = "Cliente 1"
            };
            Thread t15 = new Thread(depositar)
            {
                Name = "Cliente 2"
            };
            Thread t16 = new Thread(depositar)
            {
                Name = "Cliente 3"
            };
            Thread t17 = new Thread(depositar)
            {
                Name = "Cliente 4"
            };
            Thread t18 = new Thread(depositar)
            {
                Name = "Cliente 5"
            };
            Thread t19 = new Thread(depositar)
            {
                Name = "Cliente 6"
            };
            Thread t20 = new Thread(depositar)
            {
                Name = "Cliente 7"
            };

            t1.Start(100.00);
            t2.Start(70.00);
            t3.Start(70.00);
            t4.Start(30.00);
            t5.Start(60.00);
            t6.Start(100.00);
            t7.Start(100.00);
            t8.Start(100.00);
            t9.Start(100.00);
            t10.Start(100.00);
            t11.Start(100.00);
            t12.Start(100.00);
            t13.Start(100.00);

            Thread.Sleep(10000);

            t14.Start(50.00);
            t15.Start(100.00);
            t16.Start(300.00);
            t17.Start(100.00);
            t18.Start(50.00);
            t19.Start(50.00);
            t20.Start(50.00);
        }
    }
}
