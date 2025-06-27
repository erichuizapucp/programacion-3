
import java.util.concurrent.Semaphore;


/**
 *
 * @author eric
 */
public class CuentaBancariaSemaforo implements ICuentaBancaria {
    private double saldo;
    private final Semaphore semaforo = new Semaphore(1); 
    
    public CuentaBancariaSemaforo(double saldo) {
        this.saldo = saldo;
    }
    
    @Override
    public void retirar(String nombreCliente, double monto) {
        try {
            semaforo.acquire();
            if (saldo >= monto) {
                System.out.println("El cliente " + nombreCliente + " va a retirar " + monto);

                this.saldo -= monto;
                System.out.println(nombreCliente + " completo el retiro, queda " + this.saldo);
            }
            else {
                System.out.println(nombreCliente + " quiere retirar " + monto + " pero no hay sificiente, esperando...");
            }
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        finally {
            semaforo.release();
        }
    }
    
    @Override
    public void depositar(String nombreCliente, double monto) {
        try {
            semaforo.acquire();
            System.out.println("El cliente " + nombreCliente + " deposito " + monto);
            
            saldo += monto;
            System.out.println("Saldo despues del deposito: " + this.saldo);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
        finally {
            semaforo.release();
        }
    }
}
