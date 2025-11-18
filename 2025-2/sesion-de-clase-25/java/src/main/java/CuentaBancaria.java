
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;



/**
 *
 * @author eric
 */
public class CuentaBancaria implements ICuentaBancaria{
    private double saldo;
    private final Lock lock = new ReentrantLock();
    private final Condition fondosSuficientes = lock.newCondition();
    
    public CuentaBancaria(double saldo) {
        this.saldo = saldo;
    }
    
    @Override
    public void retirar(String nombreCliente, double monto) {
        lock.lock();
        try {
            while (this.saldo < monto) {
                System.out.println(nombreCliente + " quiere retirar " + monto + " pero no hay sificiente, esperando...");
                try {
                    fondosSuficientes.await();
                }
                catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }

            System.out.println("El cliente " + nombreCliente + " va a retirar " + monto);

            this.saldo -= monto;
            System.out.println(nombreCliente + " completo el retiro, queda " + this.saldo);
        }
        finally {
            lock.unlock();
        }
    }
    
    @Override
    public void depositar(String nombreCliente, double monto) {
        lock.lock();
        try {
            System.out.println("El cliente " + nombreCliente + " deposito " + monto);
            
            saldo += monto;
            System.out.println("Saldo despues del deposito: " + this.saldo);
            fondosSuficientes.signalAll();
        }
        finally {
            lock.unlock();
        }
    }
}
