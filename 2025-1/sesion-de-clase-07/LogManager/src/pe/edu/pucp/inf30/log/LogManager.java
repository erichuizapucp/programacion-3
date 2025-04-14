package pe.edu.pucp.inf30.log;

/**
 *
 * @author eric
 */
public class LogManager {
    public static void logInfo(String mensaje) {
        System.out.println(mensaje);
    }
    
    public static void logError(String mensaje) {
        System.err.println(mensaje);
    }
}