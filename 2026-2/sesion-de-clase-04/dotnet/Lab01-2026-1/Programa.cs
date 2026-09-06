namespace Pucp.Lab.Principal;

using Pucp.Lab.Admin;

public class Programa
{
    public static void Main(string[] args)
    {
        QuestionManager questionManager = new QuestionManager();

        Question sc1 = new SingleChoiceQuestion(1,
                "1 + 1?",
                ["1", "2", "11", "0"],
                2);
        Question sc2 = new SingleChoiceQuestion(2,
                "2 * 4?",
                ["24", "2", "8", "16"],
                3);
        Question mc1 = new MultipleChoiceQuestion(3,
                "is a planet?",
                ["Earth", "Moon", "Europe", "Mars"],
                [1, 4]);

        questionManager.Add(sc1);
        questionManager.Add(sc2);
        questionManager.Add(mc1);

        List<Question> questionsSelected = questionManager.SelectRandomN(3);
        Assessment assessment = new(
                120,
                DateTime.Now,
                GetItems(questionsSelected)
        );

        AssessmentViewerConsole.Show(assessment);
    }

    public static List<AssessmentItem> GetItems(
            List<Question> questionsSelected)
    {
        List<AssessmentItem> items = new List<AssessmentItem>();
        foreach (Question pregunta in questionsSelected)
        {
            if (pregunta is SingleChoiceQuestion)
            {
                items.Add(new AssessmentItem(pregunta, 5));
            }
            if (pregunta is MultipleChoiceQuestion)
            {
                items.Add(new AssessmentItem(pregunta, 10));
            }
        }

        return items;
    }
}
