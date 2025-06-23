/**
 *
 * @author eric
 */
public class Programa2 {
    public static void main(String[] args) {
        Runnable productor = new ProductorID();
        
        Thread p1 = new Thread(productor);
        Thread p2 = new Thread(productor);
        Thread p3 = new Thread(productor);
        
        p1.start();
        p2.start();        
        p3.start();

//        try {
//            p1.join();
//            p2.join();
//            p3.join();
//        }
//        catch (InterruptedException ex){
//        }

//        System.out.println();
//        System.out.println("Idenficador final:" + Identificador.obtenerId());
    }
}
