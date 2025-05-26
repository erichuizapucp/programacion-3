using System;
using System.IO;

public abstract class Alumno {
	private readonly char tipo;
    private readonly int codigo;
    private readonly string nombre;
    private readonly string apellido;
    private readonly int edad;
    private readonly int ciclo;
    private readonly double promedio;
	
	public Alumno() {
	}
	
	public Alumno(char tipo) {
        this.tipo = tipo;
    }
	
	public Alumno(Builder builder) {
		this.tipo = builder.Tipo;
		this.codigo = builder.Codigo;
		this.nombre = builder.Nombre;
		this.apellido = builder.Apellido;
		this.edad = builder.Edad;
		this.ciclo = builder.Ciclo;
		this.promedio = builder.Promedio;
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
	
	public class Builder : IAlumnoBuilder {
		private char tipo;
		private int codigo;
		private string nombre;
		private string apellido;
		private int edad;
		private int ciclo;
		private double promedio;
		
		public char Tipo { get { return tipo; } }
		public int Codigo { get { return codigo; } }
		public string Nombre { get { return nombre; } }
		public string Apellido { get { return apellido; } }
		public int Edad { get { return edad; } }
		public int Ciclo { get { return ciclo; } }
		public double Promedio { get { return promedio; } }
		
		public Builder() {}
		
		public Builder(Builder builder) {
			this.tipo = builder.Tipo;
			this.codigo = builder.Codigo;
			this.nombre = builder.Nombre;
			this.apellido = builder.Apellido;
			this.edad = builder.Edad;
			this.ciclo = builder.Ciclo;
			this.promedio = builder.Promedio;
		}
		
		public IAlumnoBuilder conTipo(char tipo) {
			this.tipo = tipo;
			return this;
		}
		
		public IAlumnoBuilder conCodigo(int codigo) {
			this.codigo = codigo;
			return this;
		}
		
		public IAlumnoBuilder conNombre(string nombre) {
			this.nombre = nombre;
			return this;
		}
		
		public IAlumnoBuilder conApellido(string apellido) {
			this.apellido = apellido;
			return this;
		}
		
		public IAlumnoBuilder conEdad(int edad) {
			this.edad = edad;
			return this;
		}
		
		public IAlumnoBuilder conCiclo(int ciclo) {
			this.ciclo = ciclo;
			return this;
		}
		
		public IAlumnoBuilder conPromedio(double promedio) {
			this.promedio = promedio;
			return this;
		}
	}
}