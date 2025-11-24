/**
 *
 * @author eric
 */
public interface Transaccionable {
    void depositar(String nombreCliente, double monto);
    void retirar(String nombreCliente, double monto);
}
