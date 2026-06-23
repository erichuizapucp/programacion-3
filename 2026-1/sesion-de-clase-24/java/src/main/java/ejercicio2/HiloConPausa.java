package ejercicio2;

public class HiloConPausa extends Thread {
    private static final long PAUSA_MILLIS = 5000L;

    @Override
    public void run() {
        try {
            Thread.sleep(PAUSA_MILLIS);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return;
        }

        System.out.println("Hilo '" + getName() + "'");
    }
}
