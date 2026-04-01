
import java.util.List;

public class Program {

    public static void main(String[] args) {
        List<String> list = List.of("cadena1", "cadena2", "cadena3", "cadena4", "cadena5");

        String r1 = getRandomString(list);
        String r2 = getRandomString(list);
        String r3 = getRandomString(list);

        System.out.println("Random String 1: " + r1);
        System.out.println("Random String 2: " + r2);
        System.out.println("Random String 3: " + r3);
    }

    public static String getRandomString(List<String> list) {
        int randomIndex = (int) (Math.random() * list.size());
        return list.get(randomIndex);
    }
}
