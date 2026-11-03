package ejercicio1;

public class Program {
    public static void main(String[] args) throws InterruptedException {
        HiloDemostrativo hilo = new HiloDemostrativo();
        hilo.setName("Hilo 1");
        //hilo.setDaemon(true);
        hilo.start();
  //      hilo.join();
        System.out.println("Hilo Principal");
    }
}
