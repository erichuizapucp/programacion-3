using System;
using PUCP.Edu.Pe.Inf30.Sesion04.Entidades;

namespace PUCP.Edu.Pe.Inf30.Sesion04 {
	public class Bodega {
		public static void Main(string[] args) {
			Producto p1 = new Producto();
			p1.Codigo = 1000;
			p1.Nombre = "Arroz";
			p1.Stock = 20;
			p1.Precio = 5.00;
			
			p1.imprimir();
		}
	}
}