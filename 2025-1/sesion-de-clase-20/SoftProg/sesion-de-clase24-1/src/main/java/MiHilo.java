

/**
 *
 * @author eric
 */
public class MiHilo extends Thread {
    @Override
    public void run() {
        System.out.println("Hilo: " + 
                Thread.currentThread().getName());
        
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}
