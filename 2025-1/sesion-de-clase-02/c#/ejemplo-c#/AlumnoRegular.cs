using System;

/**
 *
 * @author erichuiza
 */
public class AlumnoRegular : Alumno {
    private int annoIngreso;
	
	public int AnnoIngreso {
		get {
			return annoIngreso;
		}
		set {
			annoIngreso = value;
		}
	}
    
    public AlumnoRegular() {
    }
    
    public AlumnoRegular(char tipo) : base(tipo) {
    }

    public override void cargar(string[] values) {
        base.cargar(values);
        annoIngreso = int.Parse(values[7]);
    }

    public override void imprimir() {
        base.imprimir();
        Console.WriteLine("Año de ingreso: " + annoIngreso);
    }
}
