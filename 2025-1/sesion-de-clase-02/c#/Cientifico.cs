using System;

public class Cientifico : Persona {
	public string especialidad;
	
	public Cientifico() {
		Console.WriteLine("Constructor de cientifico.");
	}
	
	public void investigar() {
		Console.WriteLine("Investigando.");
	}
}