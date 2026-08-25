
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionManager {

    private List<Question> preguntas;

    public QuestionManager() {
        this.preguntas = new ArrayList<>();
    }

    public List<Question> getPreguntas() {
        return Collections.unmodifiableList(preguntas);
    }

    public void add(Question question) {
        preguntas.add(question);
    }

    public List<Question> selectRandomN(int cantidad) {
        List<Question> copia = new ArrayList<>(this.preguntas);
        Collections.shuffle(copia);
        return copia.subList(0, Math.min(cantidad, copia.size()));
    }
}
