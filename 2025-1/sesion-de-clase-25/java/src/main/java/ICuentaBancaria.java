/**
 *
 * @author eric
 */
public interface ICuentaBancaria {
    void depositar(String nombreCliente, double monto);
    void retirar(String nombreCliente, double monto);
}
