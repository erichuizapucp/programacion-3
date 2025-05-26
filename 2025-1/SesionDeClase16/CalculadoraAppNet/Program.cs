using System;

namespace CalculadoraAppNet {
    public class Program {
        static void Main(string[] args) {
            Calculadora.CalculadoraClient client = 
                new Calculadora.CalculadoraClient();
            Console.WriteLine(client.sumar(3, 2));
            Console.ReadLine();
        }
    }
}
