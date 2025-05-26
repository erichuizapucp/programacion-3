using System;
using System.Collections.Generic;
using System.IO;

/**
 *
 * @author erichuiza
 */
public class Universidad {
    private List<Alumno> alumnos;
    
    public Universidad() {
        alumnos = new List<Alumno>();
    }
    
    public void cargarAlumnos(String nombreArchivo) {
		FileInfo archivo = new FileInfo(nombreArchivo);
        cargarAlumnos(archivo);
    }
    
    public void cargarAlumnos(FileInfo archivo) {
		using (StreamReader reader = new StreamReader(archivo.OpenRead())) {
			while (!reader.EndOfStream) {
				string line = reader.ReadLine();
				string[] values = line.Split(',');
				
				char tipo = values[0][0];
				Alumno.Builder baseBuilder = createBaseBuilder(values);
				Alumno alumno;				
				switch (tipo) {
					case 'I':
						alumno = buildAlumnoIntercambio(baseBuilder, values);
						break;
					case 'R': 
						alumno = buildAlumnoRegular(baseBuilder, values);
						break;
					default:
						alumno = buildAlumnoRegular(baseBuilder, values);
						break;
				}
				
				alumnos.Add(alumno);
			}
		}
    }
	
	protected Alumno.Builder createBaseBuilder(string[] values) {
		char tipo = values[0][0];
		int codigo = int.Parse(values[1]);
		string nombre = values[2];
		string apellido = values[3];
		int edad = int.Parse(values[4]);
		int ciclo = int.Parse(values[5]);
		double promedio = double.Parse(values[6]);
		
		Alumno.Builder baseBuilder = new Alumno.Builder();
		baseBuilder.conTipo(tipo).
			conCodigo(codigo).
			conNombre(nombre).
			conApellido(apellido).
			conEdad(edad).
			conCiclo(ciclo).
			conPromedio(promedio);
			
		return baseBuilder;
	}
	
	protected Alumno buildAlumnoRegular(Alumno.Builder baseBuilder, string[] values) {
		int annoIngreso = int.Parse(values[7]);
		return new AlumnoRegular.Builder(baseBuilder).
			conAnnoIngreso(annoIngreso).
			build();
	}
	
	protected Alumno buildAlumnoIntercambio(Alumno.Builder baseBuilder, string[] values) {
		string paisOrigen = values[7];
		string universidadOrigen = values[8];
		int duracion = int.Parse(values[9]); 
					
		return new AlumnoIntercambio.Builder(baseBuilder).
			conPaisOrigen(paisOrigen).
			conUniversidadOrigen(universidadOrigen).
			conDuracion(duracion).
			build();
	}
    
    public void imprimirAlumnos() {
        foreach (Alumno alumno in alumnos) {
            alumno.imprimir();
            Console.WriteLine("---------------------------------------------");
        }
    }
}