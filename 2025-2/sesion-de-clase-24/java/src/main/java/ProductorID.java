/**
 *
 * @author eric
 */
public class ProductorID implements Runnable {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            Identificador.incrementar();
        } 
    }
}
