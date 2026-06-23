package ejercicio3;

public class Program {
    private static Thread[] crearProductores(int cantidad, Runnable tarea) {
        Thread[] productores = new Thread[cantidad];
        for (int i = 0; i < cantidad; i++) {
            productores[i] = new Thread(tarea, "Productor-" + (i + 1));
        }
        return productores;
    }

    private static Thread[] crearConsumidores(int cantidad, Runnable tarea) {
        Thread[] consumidores = new Thread[cantidad];
        for (int i = 0; i < cantidad; i++) {
            consumidores[i] = new Thread(tarea, "Consumidor-" + (i + 1));
        }
        return consumidores;
    }

    private static void iniciar(Thread[] hilos) {
        for (Thread hilo : hilos) {
            hilo.start();
        }
    }

    private static void esperar(Thread[] hilos) {
        for (Thread hilo : hilos) {
            try {
                hilo.join();
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.out.println("Hilo principal interrumpido");
                return;
            }
        }
    }

    public static void main(String[] args) {
        Runnable productor = new ProductorID();
        Runnable consumidor = new ConsumidorID();

        Thread[] productores = crearProductores(3, productor);
        Thread[] consumidores = crearConsumidores(3, consumidor);

        iniciar(productores);
        iniciar(consumidores);

        esperar(productores);
        esperar(consumidores);

        System.out.println();
        System.out.println("Identificador final: " + Identificador.obtenerId());
    }
}
