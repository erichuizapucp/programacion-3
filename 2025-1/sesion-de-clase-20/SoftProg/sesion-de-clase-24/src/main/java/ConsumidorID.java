/**
 *
 * @author eric
 */
public class ConsumidorID implements Runnable {
    @Override
    public void run() {
        System.out.println(Identificador.obtenerId());
    }
}
