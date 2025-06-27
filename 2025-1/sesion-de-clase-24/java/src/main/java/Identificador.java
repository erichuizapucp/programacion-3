/**
 *
 * @author eric
 */
public class Identificador {
    private static int id = 0;
    // private static final Object lock = new Object();

    public synchronized static void incrementar() {
//        synchronized (lock) {
//            id++;
//            System.out.println(id);
//        }
        id++;
        System.out.println(id);
    }

    public static int obtenerId() {
        return id;
    }
}