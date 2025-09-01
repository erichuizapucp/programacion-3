package pe.edu.pucp.inf30;

import pe.edu.pucp.inf30.softprog.config.CadenaConexion;

/**
 *
 * @author eric
 */
public class app {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        CadenaConexion cadena = new CadenaConexion.Builder()
                .servidor("localhost")
                .schema("softprog")
                .puerto(3306)
                .build();
        
        System.out.println(cadena);
    }
    
}
