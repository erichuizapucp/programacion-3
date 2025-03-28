
import java.util.Scanner;

/**
 *
 * @author erichuiza
 */
class AlumnoRegular extends Alumno {
    private int annoIngreso;
    
    public AlumnoRegular() {
        super();
    }
    
    public AlumnoRegular(char tipo) {
        super(tipo);
    }
    
    public void setAnnoIngreso(int annoIngreso) {
        this.annoIngreso = annoIngreso;
    }

    public int getAnnoIngreso() {
        return annoIngreso;
    }

    @Override
    public void cargar(Scanner scanner) {
        super.cargar(scanner);
        annoIngreso = scanner.nextInt();
    }

    @Override
    public void imprimir() {
        super.imprimir();
        System.out.println("Año de ingreso: " + annoIngreso);
    }
}
