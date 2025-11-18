/**
 *
 * @author eric
 */
public class Deposito implements Runnable {
    private final double monto;
    private final CuentaBancaria cuenta;
    
    public Deposito(CuentaBancaria cuenta, double monto) {
        this.monto = monto;
        this.cuenta = cuenta;
    }
    
    @Override
    public void run() {
        String nombreCliente = Thread.currentThread().getName();
        this.cuenta.depositar(nombreCliente, monto);
    }
}
