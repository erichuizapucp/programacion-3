
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author eric
 */
public class ProgramaSemaforos {
    public static void main(String[] args) {
        ICuentaBancaria cuenta = new CuentaBancariaSemaforo(500.00);
        
        Runnable retirar100 = new Retiro(cuenta, 100.00);
        Runnable retirar70 = new Retiro(cuenta, 70.00);
        Runnable retirar60 = new Retiro(cuenta, 60.00);
        Runnable retirar30 = new Retiro(cuenta, 30.00);
        
        List<Runnable> retiros = List.of(
            retirar100,
            retirar70,
            retirar70,
            retirar30,
            retirar60
        );
        
        ExecutorService pool = Executors.newFixedThreadPool(20);
        try {
            System.out.println("=== Iniciando retiros ===");
            retiros.forEach(r -> pool.submit(r));
        }
        finally {
            pool.shutdown();
        }
    }
}
