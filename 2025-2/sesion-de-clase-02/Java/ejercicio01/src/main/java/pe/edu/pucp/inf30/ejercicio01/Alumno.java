package pe.edu.pucp.inf30.ejercicio01;

/**
 *
 * @author eric
 */
public class Alumno extends Persona implements IPracticable {
    private TipoActividad actividad;
    
    public Alumno() {
        System.out.println("Alumno: Constructor");
        this.actividad = TipoActividad.Ninguna;
    }
    
    public TipoActividad getActividad() {
        return this.actividad;
    }
    
    public void setTipoActividad(TipoActividad actividad) {
        this.actividad = actividad;
    }
    
    Alumno(AlumnoBuilder builder) {
        super(builder);
        this.actividad = builder.getActividad();
    }
    
    @Override
    public String toString() {
        return String.format("[Alumno] Nombre: %s, Edad: %d, "
                + "Actividad: %s", 
                this.getNombre(), this.getEdad(), this.actividad);
    }
    
    @Override
    public void imprimir() {
        System.out.println(this);
    }

    @Override
    public void practicar() {
        System.out.println("El alumno esta practicando: " + this.actividad);
    }
    
    public static class AlumnoBuilder extends Persona.PersonaBuilder {
        private TipoActividad actividad = TipoActividad.Ninguna;
        
        public TipoActividad getActividad() {
            return this.actividad;
        }

        @Override
        public AlumnoBuilder nombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        @Override
        public AlumnoBuilder edad(int edad) {
            this.edad = edad;
            return this;
        }
        
        public AlumnoBuilder actividad(TipoActividad actividad) {
            this.actividad = actividad;
            return this;
        }
        
        @Override
        public Alumno build() {
            return new Alumno(this);
        }
    }
}
