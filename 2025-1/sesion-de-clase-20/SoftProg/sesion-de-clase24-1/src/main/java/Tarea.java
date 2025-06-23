/**
 *
 * @author eric
 */
public class Tarea implements Runnable {
    @Override
    public void run() {
        System.out.println("Hilo: " + 
                Thread.currentThread().getName());
    }
}
