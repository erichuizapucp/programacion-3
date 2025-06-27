/**
 *
 * @author eric
 */
public class Programa1 {

    public static void main(String[] args) {
        MiHilo hilo = new MiHilo();
        hilo.setName("Hilo 1");
        hilo.start();
        
        try {
            hilo.join();
        }
        catch (InterruptedException ex) {
        }
        
        System.out.println("Hilo Principal");
    }
}
