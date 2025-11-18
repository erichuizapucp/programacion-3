/**
 *
 * @author eric
 */
public class Identificador {
    private static int id = 0;
//    private static final Object lock = new Object();

    // Se establece un area critica
    // Se establece un MUTEX o exclusion mutua
    public synchronized static void incrementar() {
//        synchronized (lock) {
//            id++;
//            System.out.println(id + ": producido por -> " + Thread.currentThread().getName());
//        }
        id++;
        System.out.println(id + ": producido por -> " + Thread.currentThread().getName());
    }

    public static int obtenerId() {
        return id;
    }
}