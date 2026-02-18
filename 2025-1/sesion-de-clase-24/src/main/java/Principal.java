

/**
 *
 * @author erichuiza
 */
public class Principal {

    public static void main(String[] args) {
        MiHilo hilo1 = new MiHilo();
        
//        System.out.println(hilo1.isAlive());
//        System.out.println(hilo1.getState());
        hilo1.start();
        hilo1.setName("Hilo 1");
        hilo1.setPriority(Thread.MAX_PRIORITY);
//        System.out.println(hilo1.getState());
//        System.out.println(hilo1.isAlive());
        
        MiHilo hilo2 = new MiHilo();
        hilo2.setName("Hilo 2");
        hilo2.start();
        
        Thread th3 = new Thread(new MiTarea());
        th3.start();
        
        Thread th4 = new Thread(new MiTarea(), "Hilo 4");
        th4.start();
    }
}
