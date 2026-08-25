
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Question implements Printable, Answerable {

    private int codigo;
    private String prompt;
    private List<String> opciones;

    public Question(int codigo, String prompt,
            List<String> opciones) {

        this.codigo = codigo;
        this.prompt = prompt;
        this.opciones = new ArrayList<>(opciones);
    }

    public int getCodigo() {
        return codigo;
    }

    public String getPrompt() {
        return prompt;
    }

    public List<String> getOpciones() {
        return Collections.unmodifiableList(opciones);
    }

    @Override
    public void devolverDatos() {
        System.out.println(prompt);
        int index = 0;
        for (String opcion : opciones) {
            System.out.println(
                    String.format("%d. %s", ++index, opcion));
        }
    }
}
