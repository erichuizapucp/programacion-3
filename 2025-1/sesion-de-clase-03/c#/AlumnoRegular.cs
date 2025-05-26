using System;

/**
 *
 * @author erichuiza
 */
public class AlumnoRegular : Alumno {
    private readonly int annoIngreso;
    
    public AlumnoRegular() {
    }
    
    public AlumnoRegular(char tipo) : base(tipo) {
    }
	
	public AlumnoRegular(Builder builder) : base(builder) {
		annoIngreso = builder.AnnoIngreso;
	}

    public override void imprimir() {
        base.imprimir();
        Console.WriteLine("Año de ingreso: " + annoIngreso);
    }
	
	public new class Builder : Alumno.Builder, IAlumnoRegularBuilder {
		private int annoIngreso;
		
		public Builder() {}
		
		public Builder(Alumno.Builder builder) : base(builder) {
		}
		
		public int AnnoIngreso { get { return annoIngreso; } }
		
		public IAlumnoRegularBuilder conAnnoIngreso(int annoIngreso) {
			this.annoIngreso = annoIngreso;
			return this;
		}
		
		public AlumnoRegular build() {
			return new AlumnoRegular(this);
		}
	}
}
