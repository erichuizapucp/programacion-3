package pe.edu.pucp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.admin.AssessmentViewerConsole;
import pe.edu.pucp.admin.QuestionManager;
import pe.edu.pucp.examenes.Assessment;
import pe.edu.pucp.examenes.AssessmentItem;
import pe.edu.pucp.preguntas.MultipleChoiceQuestion;
import pe.edu.pucp.preguntas.Question;
import pe.edu.pucp.preguntas.SingleChoiceQuestion;

public class Programa {

    public static void main(String[] args) {
        QuestionManager questionManager = new QuestionManager();

        Question sc1 = new SingleChoiceQuestion(1,
                "1 + 1?",
                List.of("1", "2", "11", "0"),
                2);
        Question sc2 = new SingleChoiceQuestion(2,
                "2 * 4?",
                List.of("24", "2", "8", "16"),
                3);
        Question mc1 = new MultipleChoiceQuestion(3,
                "is a planet?",
                List.of("Earth", "Moon", "Europe", "Mars"),
                List.of(1, 4));

        questionManager.add(sc1);
        questionManager.add(sc2);
        questionManager.add(mc1);

        List<Question> questionsSelected = questionManager.selectRandomN(3);
        Assessment assessment = new Assessment(
                120,
                LocalDateTime.now(),
                getItems(questionsSelected)
        );

        AssessmentViewerConsole.show(assessment);
    }

    public static List<AssessmentItem> getItems(
            List<Question> questionsSelected) {

        List<AssessmentItem> items = new ArrayList<>();
        for (Question pregunta : questionsSelected) {
            if (pregunta instanceof SingleChoiceQuestion) {
                items.add(new AssessmentItem(pregunta, 5));
            }
            if (pregunta instanceof MultipleChoiceQuestion) {
                items.add(new AssessmentItem(pregunta, 10));
            }
        }

        return items;
    }
}
