using System;
using PUCP.Inf30.SoftProg.Config;

namespace PUCP.Inf30.SoftProg {
	public class Principal {
		public static void Main(string[] args) {
			CadenaConexion cadenaConexion = new CadenaConexion.Builder()
				.ConServidor("127.0.0.1")
				.ConSchema("INF30")
				.EnPuerto(3306)
				.Build();
				
			Console.WriteLine(cadenaConexion);
		}
	}
}
