import java.util.List;

public class Programa {
    public static void main(String[] args) {
        Inscribible i1 = new Alumno();
        Inscribible i2 = new Departamento();
        Inscribible i3 = new Vehiculo();

        GestorDeInscripciones g = new GestorDeInscripciones();
        g.inscribir(List.of(i1, i2, i3));
    }
}
