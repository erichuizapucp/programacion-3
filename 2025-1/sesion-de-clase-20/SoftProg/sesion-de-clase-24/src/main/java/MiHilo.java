/**
 *
 * @author eric
 */
public class MiHilo extends Thread {
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex) {
        }
        
        System.out.println("Hilo '" + this.getName() + "'");
    }
}
