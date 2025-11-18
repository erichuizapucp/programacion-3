/**
 *
 * @author eric
 */
public class Retiro implements Runnable {
    private final double monto;
    private final ICuentaBancaria cuenta;
    
    public Retiro(ICuentaBancaria cuenta, double monto) {
        this.monto = monto;
        this.cuenta = cuenta;
    }
    
    @Override
    public void run() {
        String nombreCliente = Thread.currentThread().getName();
        this.cuenta.retirar(nombreCliente, monto);
    }
}
