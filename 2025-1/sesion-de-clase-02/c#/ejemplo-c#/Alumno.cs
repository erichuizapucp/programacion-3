using System;
using System.IO;

public abstract class Alumno {
	private char tipo;
    private int codigo;
    private string nombre;
    private string apellido;
    private int edad;
    private int ciclo;
    private double promedio;
	
	public Alumno() {
		Console.WriteLine("Constructor Alumno.");
	}
	
	public Alumno(char tipo) {
        this.tipo = tipo;
    }
	
	public virtual void cargar(string[] values) {
        codigo = int.Parse(values[1]);
        nombre = values[2];
        apellido = values[3];
        edad = int.Parse(values[4]);
        ciclo = int.Parse(values[5]);
        promedio = double.Parse(values[6]);
    }
    
    public virtual void imprimir() {
        Console.WriteLine("Tipo: " + tipo);
        Console.WriteLine("Codigo: " + codigo);
        Console.WriteLine("Nombre: " + nombre);
        Console.WriteLine("Apellido: " + apellido);
        Console.WriteLine("Edad: " + edad);
        Console.WriteLine("Ciclo: " + ciclo);
        Console.WriteLine("Promedio: " + promedio);
    }
}