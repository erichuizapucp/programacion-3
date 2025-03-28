using System;

public class Programa {
	public static void Main(string[] args) {
		using (Persona a = new Persona()) {
		}
		// a.Dispose();
		
		Cientifico b = new Cientifico();
		b.investigar();
		b.Dispose();
	}
}