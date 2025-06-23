using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading;
using System.Threading.Tasks;

namespace sesion_de_clase_24_net
{
    class CuentaBancaria
    {
        private double saldo;
        private readonly Object lockObj = new Object();

        public CuentaBancaria(double saldo) { 
            this.saldo = saldo;
        }

        public void Retirar(string nombreCliente, double monto) {
            lock (lockObj) {
                while (saldo < monto)
                {
                    Console.WriteLine(nombreCliente + " quiere retirar " + monto + " pero no hay suficiente, esperando...");
                    Monitor.Wait(lockObj);
                }

                Console.WriteLine(nombreCliente + " va a retirar " + monto);
                this.saldo -= monto;
                Console.WriteLine(nombreCliente + " retiro " + monto + " queda " + this.saldo);
            }
        }

        public void Depositar(string nombreCliente, double monto) {
            lock (lockObj) {
                Console.WriteLine(nombreCliente + " deposito " + monto);

                this.saldo += monto;

                Console.WriteLine("Saldo luego del deposito: " + this.saldo);

                Monitor.PulseAll(lockObj);
            }
        }

    }
}
