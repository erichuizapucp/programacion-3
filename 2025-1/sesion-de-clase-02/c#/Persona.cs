using System;

public class Persona : IDisposable {
	private string nombre;
	private int edad;
	
	private bool disposed = false;
	
	public Persona() {
		Console.WriteLine("El objeto se esta construyendo.");
	}
	
	public void Dispose() {
		Console.WriteLine("El objeto se esta liberando de forma explicita.");
		GC.SuppressFinalize(this);
	}
	
	~Persona() {
		Console.WriteLine("El objeto se esta destruyendo.");
	}
}