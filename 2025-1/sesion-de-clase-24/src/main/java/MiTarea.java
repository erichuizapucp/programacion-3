/**
 *
 * @author erichuiza
 */
public class MiTarea implements Runnable {
    @Override
    public void run() {
        System.out.println("El hilo: " + Thread.currentThread().getName() + ", fue ejecutado.");
    }
}
