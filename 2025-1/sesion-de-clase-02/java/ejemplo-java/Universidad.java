import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author erichuiza
 */
public class Universidad {
    private List<Alumno> alumnos;
    
    public Universidad() {
        alumnos = new ArrayList<>();
    }
    
    public void cargarAlumnos(String nombreArchivo) throws Exception {
        File archivo = new File(nombreArchivo);
        cargarAlumnos(archivo);
    }
    
    public void cargarAlumnos(File archivo) throws Exception {
        Scanner scanner = new Scanner(archivo);
        scanner.useDelimiter(",|\\n");
			
        while (scanner.hasNext()) {
            char tipo = scanner.next().charAt(0);
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
            alumno.cargar(scanner);
            alumnos.add(alumno);
        }
    }
    
    public void imprimirAlumnos() {
        for (Alumno alumno : alumnos) {
            alumno.imprimir();
            System.out.println("---------------------------------------------");
        }
    }
}
