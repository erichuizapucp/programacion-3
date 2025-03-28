/**
 *
 * @author erichuiza
 */
public class Principal {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        Universidad universidad = new Universidad();
        universidad.cargarAlumnos("alumnos.csv");
        universidad.imprimirAlumnos();
    }
}
