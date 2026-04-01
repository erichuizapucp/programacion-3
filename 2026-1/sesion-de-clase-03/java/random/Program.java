
import java.util.List;

public class Program {

    public static void main(String[] args) {
        // List<String> list = new ArrayList<>();
        // list.add("Hello");
        // list.add("World");
        // list.add("Java");
        // list.add("Programming");
        // list.add("C#");
        // list.add("Prog03");

        List<String> list
                = List.of("Hello", "World", "Java", "Programming", "C#", "Prog03");

        String randomString = getRandomString(list);
        System.out.println("Random String: " + randomString);
    }

    public static String getRandomString(List<String> list) {
        int randomIndex = (int) (Math.random() * list.size());
        return list.get(randomIndex);
    }
}
