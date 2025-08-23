package pe.edu.pucp.inf30.ejercicio01;

/**
 *
 * @author eric
 */
public class Programa {
    public static void main(String[] args) {
        Persona persona = new Alumno.AlumnoBuilder()
                .nombre("Juan")
                .edad(22)
                .build(); 
//        System.out.println(persona);
        persona.imprimir();

        IPracticable alumno = new Alumno.AlumnoBuilder()
                .nombre("Ana")
                .edad(21)
                .actividad(TipoActividad.Musica)
                .build();
        alumno.practicar();
    }
}
