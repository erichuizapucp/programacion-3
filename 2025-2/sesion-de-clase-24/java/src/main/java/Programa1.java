/**
 *
 * @author eric
 */
public class Programa1 {

    public static void main(String[] args) {
        MiHilo hilo = new MiHilo();
        System.out.println(hilo.getState());
        hilo.setName("Hilo 1");
//        hilo.run(); // aqui NO SE CREA el hilo de ejecucion
        hilo.start(); // aqui se crea el hilo de ejecucion
        System.out.println(hilo.getState());
        try {
            hilo.join();
//            System.out.println(Thread.currentThread().getState());
        }
        catch (InterruptedException ex) {
        }
        System.out.println(hilo.getState());
        
        System.out.println("Hilo Principal");
    }
}
