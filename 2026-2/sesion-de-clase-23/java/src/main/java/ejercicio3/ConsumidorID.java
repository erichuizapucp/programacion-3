package ejercicio3;


public class ConsumidorID implements Runnable {
    @Override
    public void run() {
        String nombreConsumidor = Thread.currentThread().getName();
        Identificador.imprimirIdActualConsumido(nombreConsumidor);
    }
}
