using System;

public class AlumnoIntercambio : Alumno {
	private readonly string paisOrigen;
    private readonly string universidadOrigen;
    private readonly int duracion;
    
    public AlumnoIntercambio() {
    }
    
    public AlumnoIntercambio(char tipo) : base(tipo) {
    }
	
	public AlumnoIntercambio(Builder builder) : base(builder) {
		this.paisOrigen = builder.PaisOrigen;
		this.universidadOrigen = builder.UniversidadOrigen;
		this.duracion = builder.Duracion;
	}

    public override void imprimir() {
        base.imprimir();
        Console.WriteLine("Pais de Origen: " + paisOrigen);
        Console.WriteLine("Universidad: " + universidadOrigen);
        Console.WriteLine("Duración: " + duracion);
    }
	
	public new class Builder : Alumno.Builder, IAlumnoIntercambioBuilder {
		private string paisOrigen;
		private string universidadOrigen;
		private int duracion;
		
		public string PaisOrigen { get { return paisOrigen; } }
		public string UniversidadOrigen { get { return universidadOrigen; } }
		public int Duracion { get { return duracion; } }
		
		public Builder() {}
		
		public Builder(Alumno.Builder builder) : base(builder) {
		}
		
		public IAlumnoIntercambioBuilder conPaisOrigen(string paisOrigen) {
			this.paisOrigen = paisOrigen;
			return this;
		}
		
		public IAlumnoIntercambioBuilder conUniversidadOrigen(string universidadOrigen) {
			this.universidadOrigen = universidadOrigen;
			return this;
		}
		
		public IAlumnoIntercambioBuilder conDuracion(int duracion) {
			this.duracion = duracion;
			return this;
		}
		
		public AlumnoIntercambio build() {
			return new AlumnoIntercambio(this);
		}
	}
}