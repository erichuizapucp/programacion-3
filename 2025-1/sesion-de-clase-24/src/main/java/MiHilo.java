/**
 *
 * @author erichuiza
 */
public class MiHilo extends Thread {
    @Override
    public void run() {
        System.out.println("El hilo: " + this.getName() + ", fue ejecutado.");
    }
}
