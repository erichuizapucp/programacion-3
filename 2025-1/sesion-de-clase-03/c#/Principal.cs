/**
 *
 * @author erichuiza
 */
public class Principal {
    /**
     * @param args the command line arguments
     */
    public static void Main(string[] args) {
        Universidad universidad = new Universidad();
        universidad.cargarAlumnos("alumnos.csv");
        universidad.imprimirAlumnos();
    }
}
