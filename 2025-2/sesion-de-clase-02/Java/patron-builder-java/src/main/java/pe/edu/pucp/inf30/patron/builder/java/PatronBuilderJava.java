package pe.edu.pucp.inf30.patron.builder.java;

/**
 *
 * @author eric
 */
public class PatronBuilderJava {
    public static void main(String[] args) {
        CadenaConexion cadena = new CadenaConexion.Builder()
            .servidor("127.0.0.1")
            .schema("INF30")
            .puerto(3306)
            .build();
        
        System.out.println(cadena);
    }
}
