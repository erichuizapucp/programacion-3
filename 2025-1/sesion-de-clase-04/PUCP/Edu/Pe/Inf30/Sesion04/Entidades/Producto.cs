using System;

namespace PUCP.Edu.Pe.Inf30.Sesion04.Entidades {
	public class Producto : EntidadBase {
		public int Stock { get; set; }
		public double Precio { get; set; }
		
		public override void imprimir() {
			base.imprimir();
			Console.WriteLine("Stock: " + this.Stock);
			Console.WriteLine("Precio: " + this.Precio);
		}
	}
}
