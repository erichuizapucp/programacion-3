using System;

namespace PatronBuilder {
    public class Program {
        public static void Main(string[] args) {
            CadenaConexion cadena = new CadenaConexion.Builder()
                .ConServidor("127.0.0.1")
                .ConSchema("INF30")
                .EnPuerto(3306)
                .Build();

            Console.WriteLine(cadena);
        }
    }
}
