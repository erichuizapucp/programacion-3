using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using SoftProgPruebas.SoftProgWS;

namespace SoftProgPruebas {
    public class Program {
        public static void Main(string[] args) {
            CalculadoraWSClient client = new CalculadoraWSClient();
            int res = client.sumar(3, 3);

            Console.WriteLine(res);

            res = client.multiplicar(5, 5);
            Console.WriteLine(res);
        }
    }
}
