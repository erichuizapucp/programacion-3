/**
 *
 * @author eric
 */
public class Programa {

    public static void main(String[] args) {
        MiHilo t1 = new MiHilo();
        t1.setName("Hilo 1");
        
        System.out.println(t1.getState());
        
        t1.start();
        
        System.out.println(t1.getState());
        
        Runnable tarea = new Tarea();
        Thread t2 = new Thread(tarea, "Hilo 2");
        t2.start();
        
        Thread t3 = new Thread(() -> {
            System.out.println("Hilo: " + 
                    Thread.currentThread().getName());
        } , "Hilo 3");
        t3.start();
        
        try {
            t1.join();
            t2.join();
            t3.join();
        }
        catch (InterruptedException ex) {
            
        }
        
        System.out.println("Hilo Principal");
    }
}
