import java.util.List;

public class GestorDeInscripciones {
    public void inscribir(Inscribible m) {
        m.inscribir();
    }

    public void inscribir(List<Inscribible> lista) {
        for (Inscribible m : lista) {
            m.inscribir();
        }
    }
}
