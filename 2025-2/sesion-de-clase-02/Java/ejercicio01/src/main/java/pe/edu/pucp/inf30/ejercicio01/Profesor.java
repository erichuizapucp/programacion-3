package pe.edu.pucp.inf30.ejercicio01;

/**
 *
 * @author eric
 */
public class Profesor extends Persona {
    public Profesor() {
        System.out.println("Profesor: Constructor");
    }
    
    @Override
    public String toString() {
        return String.format("[Profesor] Nombre: %s, Edad: %d", this.getNombre(), this.getEdad());
    }
    
    @Override
    public void imprimir() {
        System.out.println(this);
    }
    
    Profesor(ProfesorBuilder builder) {
        super(builder);
    }
    
    public static class ProfesorBuilder extends Persona.PersonaBuilder {
        @Override
        public Profesor build() {
            return new Profesor(this);
        }
    }
}
