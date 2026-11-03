import java.util.concurrent.Semaphore;

public class CuentaBancariaSemaforo implements Transaccionable {

    private double saldo;
    private final Semaphore mutex = new Semaphore(1);
    private final Semaphore saldoSuficiente = new Semaphore(0);

    public CuentaBancariaSemaforo(double saldoInicial) {
        this.saldo = saldoInicial;
        if (saldoInicial > 0) {
            saldoSuficiente.release();
        }
    }

    @Override
    public void retirar(String nombreCliente, double monto) {
        try {
            while (true) {
                saldoSuficiente.acquire();

                mutex.acquire();
                if (saldo >= monto) {
                    System.out.println(nombreCliente + " retira " + monto);
                    saldo -= monto;
                    System.out.println("Saldo queda: " + saldo);

                    if (saldo > 0) saldoSuficiente.release();

                    mutex.release();
                    return;
                }
                mutex.release();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void depositar(String nombreCliente, double monto) {
        try {
            mutex.acquire();

            System.out.println(nombreCliente + " deposita " + monto);
            saldo += monto;
            System.out.println("Saldo queda: " + saldo);

            saldoSuficiente.release();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            mutex.release();
        }
    }
}
