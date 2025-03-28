
import java.util.Scanner;

/**
 *
 * @author erichuiza
 */
class AlumnoIntercambio extends Alumno {
    private String paisOrigen;
    private String universidadOrigen;
    private int duracion;
    
    public AlumnoIntercambio() {
        super();
    }
    
    public AlumnoIntercambio(char tipo) {
        super(tipo);
    }
    
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }

    public void setUniversidadOrigen(String universidadOrigen) {
        this.universidadOrigen = universidadOrigen;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    } 

    public String getPaisOrigen() {
        return paisOrigen;
    }

    public String getUniversidadOrigen() {
        return universidadOrigen;
    }

    public int getDuracion() {
        return duracion;
    }

    @Override
    public void cargar(Scanner scanner) {
        super.cargar(scanner);
        paisOrigen = scanner.next();
        universidadOrigen = scanner.next();
        duracion = scanner.nextInt(); 
    }

    @Override
    public void imprimir() {
        super.imprimir();
        System.out.println("Pais de Origen: " + paisOrigen);
        System.out.println("Universidad: " + universidadOrigen);
        System.out.println("Duración: " + duracion);
    }
}
