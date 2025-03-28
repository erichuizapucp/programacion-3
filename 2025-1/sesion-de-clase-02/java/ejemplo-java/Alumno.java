
import java.util.Scanner;

/**
 *
 * @author erichuiza
 */
abstract class Alumno {
    private char tipo;
    private int codigo;
    private String nombre;
    private String apellido;
    private int edad;
    private int ciclo;
    private double promedio;
    
    public Alumno() {
        System.out.println("Constructor Alumno.");
    }
    
    public Alumno(char tipo) {
        this.tipo = tipo;
    }
    
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public char getTipo() {
        return tipo;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public int getCiclo() {
        return ciclo;
    }

    public double getPromedio() {
        return promedio;
    }
    
    public void cargar(Scanner scanner) {
        codigo = scanner.nextInt();
        nombre = scanner.next();
        apellido = scanner.next();
        edad = scanner.nextInt();
        ciclo = scanner.nextInt();
        promedio = scanner.nextDouble();
    }
    
    public void imprimir() {
        System.out.println("Tipo: " + tipo);
        System.out.println("Codigo: " + codigo);
        System.out.println("Nombre: " + nombre);
        System.out.println("Apellido: " + apellido);
        System.out.println("Edad: " + edad);
        System.out.println("Ciclo: " + ciclo);
        System.out.println("Promedio: " + promedio);
    }
}
