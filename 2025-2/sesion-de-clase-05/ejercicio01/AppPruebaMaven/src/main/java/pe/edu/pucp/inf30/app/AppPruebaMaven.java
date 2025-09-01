package pe.edu.pucp.inf30.app;

import pe.edu.pucp.inf30.softprog.config.CadenaConexion;

/**
 *
 * @author eric
 */
public class AppPruebaMaven {

    public static void main(String[] args) {
        CadenaConexion cadena = new CadenaConexion.Builder()
                .servidor("127.0.0.0")
                .schema("softprog")
                .puerto(3306)
                .build();
        
        System.out.println(cadena);
        
    }
}
