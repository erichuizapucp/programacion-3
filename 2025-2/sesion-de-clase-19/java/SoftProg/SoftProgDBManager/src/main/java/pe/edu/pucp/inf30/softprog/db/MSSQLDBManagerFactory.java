package pe.edu.pucp.inf30.softprog.db;

/**
 *
 * @author eric
 */
public class MSSQLDBManagerFactory extends DBManagerFactory { 

    @Override
    public DBManager crearDBManager(String host, int puerto, String esquema, 
                                    String usuario, String password) {
        return MSSQLDBManager.getInstance(host, puerto, esquema, usuario, 
                                          password);
    }
}
