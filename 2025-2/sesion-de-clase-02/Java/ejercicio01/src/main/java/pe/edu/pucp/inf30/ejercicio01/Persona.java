package pe.edu.pucp.inf30.ejercicio01;

/**
 *
 * @author eric
 */
public abstract class Persona {
    private String nombre;
    private int edad;
    
    public Persona() {
        System.out.println("Persona: Constructor");
    }
    
    public String getNombre() {
        return this.nombre;
    }
    
    public int getEdad() {
        return this.edad;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setEdad(int edad) {
        this.edad = edad;
    }
    
    Persona(PersonaBuilder builder) {
        this.nombre = builder.getNombre();
        this.edad = builder.getEdad();
    }
    
    @Override
    public String toString() {
        return String.format("Nombre: %s, Edad: %d", this.nombre, this.edad);
    }
    
    public abstract void imprimir();
    
    public abstract static class PersonaBuilder {
        protected String nombre;
        protected int edad;
        
        public String getNombre() {
            return this.nombre;
        }
        
        public int getEdad() {
            return this.edad;
        }
        
        public PersonaBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        
        public PersonaBuilder edad(int edad) {
            this.edad = edad;
            return this;
        }
        
        public abstract Persona build();
    }
}
