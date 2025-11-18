
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 *
 * @author eric
 */
public class Programa {
    public static void main(String[] args) {
        CuentaBancaria cuenta = new CuentaBancaria(500.00);
        
        Runnable retirar100 = new Retiro(cuenta, 100.00);
        Runnable retirar70 = new Retiro(cuenta, 70.00);
        Runnable retirar60 = new Retiro(cuenta, 60.00);
        Runnable retirar30 = new Retiro(cuenta, 30.00);
        
        Runnable depositar100 = new Deposito(cuenta, 100.00);
        Runnable depositar50 = new Deposito(cuenta, 50.00);
        Runnable depositar300 = new Deposito(cuenta, 300.00);
        
        List<Runnable> retiros = List.of(
            retirar100,
            retirar70,
            retirar70,
            retirar30,
            retirar60,
            retirar100,
            retirar100,
            retirar100,
            retirar100,
            retirar100,
            retirar100,
            retirar100,
            retirar100
        );
        
        List<Runnable> depositos = List.of(
            depositar50,
            depositar100,
            depositar300,
            depositar100,
            depositar50,
            depositar50,
            depositar50
        );
        
        ExecutorService pool = Executors.newFixedThreadPool(20);
        try {
            System.out.println("=== Iniciando retiros ===");
            retiros.forEach(r -> pool.submit(r));
            
            System.out.println("Esperando antes de depositos...");
            Thread.sleep(10000);
            
            System.out.println("=== Iniciando depositos ===");  
            depositos.forEach(d -> pool.submit(d));
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        finally {
            pool.shutdown();
        }
    }
}
