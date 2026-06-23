package ejercicio2;

public class Program {
    private static void imprimirEstado(String mensaje, Thread hilo) {
        System.out.println(mensaje + ": " + hilo.getState());
    }

    private static void esperarFinalizacion(Thread hilo) {
        try {
            hilo.join();
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            System.out.println("Hilo principal interrumpido");
        }
    }

    public static void main(String[] args) {
        HiloConPausa hilo = new HiloConPausa();
        hilo.setName("Hilo 1");

        imprimirEstado("Estado inicial", hilo); // NEW
        hilo.start();
        imprimirEstado("Estado luego de start", hilo); // RUNNABLE/TIMED_WAITING
        esperarFinalizacion(hilo);
        imprimirEstado("Estado final", hilo); // TERMINATED
        System.out.println("Hilo Principal");
    }
}
