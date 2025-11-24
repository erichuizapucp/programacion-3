

/**
 *
 * @author eric
 */
public class CuentaBancaria {
    private double saldo = 500.00;
    
    public synchronized void retirar(String nombreCliente, double monto) {
        while (this.saldo < monto) {
            System.out.println(nombreCliente + " quiere retirar " + monto + " pero no hay sificiente, esperando...");
            try {
                wait();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        }
               
        System.out.println("El cliente " + nombreCliente + " va a retirar " + monto);

        this.saldo -= monto;
        System.out.println(nombreCliente + " completo el retiro, queda " + this.saldo);
    }
    
    public synchronized void depositar(String nombreCliente, double monto) {
        System.out.println("El cliente " + nombreCliente + " deposito " + monto);
        saldo += monto;
        System.out.println("Saldo despues del deposito: " + this.saldo);
        notifyAll();
    }
}
