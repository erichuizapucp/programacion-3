package ejercicio1;

public class Program {
    public static void main(String[] args) {
        HiloDemostrativo hilo = new HiloDemostrativo();
        hilo.setName("Hilo 1");
        hilo.start();
        System.out.println("Hilo Principal");
    }
}
