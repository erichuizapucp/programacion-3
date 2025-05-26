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
				Alumno alumno;
				switch (tipo) {
					case 'I':
						alumno = new AlumnoIntercambio(tipo);
						break;
					case 'R': 
						alumno = new AlumnoRegular(tipo);
						break;
					default:
						alumno = new AlumnoRegular(tipo);
						break;
				}
				
				alumno.cargar(values);
				alumnos.Add(alumno);
			}
		}
    }
    
    public void imprimirAlumnos() {
        foreach (Alumno alumno in alumnos) {
            alumno.imprimir();
            Console.WriteLine("---------------------------------------------");
        }
    }
}