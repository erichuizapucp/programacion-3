using System;

namespace PUCP.Edu.Pe.Inf30.Sesion04.Entidades {
	public abstract class EntidadBase {
		public int Codigo { get; set; }
		public string Nombre { get; set; }
		
		public virtual void imprimir() {
			Console.WriteLine("Codigo: " + this.Codigo);
			Console.WriteLine("Nombre: " + this.Nombre);
		}
	}
}
