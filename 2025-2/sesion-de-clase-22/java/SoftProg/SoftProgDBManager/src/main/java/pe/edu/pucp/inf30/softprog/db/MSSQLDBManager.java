package pe.edu.pucp.inf30.softprog.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import pe.edu.pucp.inf30.softprog.db.utils.TipoDB;

/**
 *
 * @author eric
 */
public class MSSQLDBManager extends DBManager {
    private static MSSQLDBManager instancia;
    
    protected MSSQLDBManager() {}
    
    protected MSSQLDBManager(String host, int puerto, String esquema, 
                          String usuario, String password) {
        super(host, puerto, esquema, usuario, password, TipoDB.MSSQL);
    }
    
    static synchronized MSSQLDBManager getInstance(String host, int puerto, 
                                                   String esquema, 
                                                   String usuario, 
                                                   String password) {
        if (instancia == null) {
            instancia = new MSSQLDBManager(host, puerto, esquema, usuario, 
                                           password);
        }
        return instancia;
    }
    
    @Override
    public Connection getConnection() throws SQLException, 
                                             ClassNotFoundException  {
        try {
            /* 
            Por ahora creamos una conexion cada vez que se necesita acceder 
            a la base de datos, por ser una aplicacion academica es una practica 
            aceptable, en un sistema productivo se debe usar un pool de 
            conexiones.
            */
            Class.forName(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String cadenaConexion = cadenaConexion();
            return DriverManager.getConnection(cadenaConexion, usuario, 
                                               password);
        }
        catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
}
