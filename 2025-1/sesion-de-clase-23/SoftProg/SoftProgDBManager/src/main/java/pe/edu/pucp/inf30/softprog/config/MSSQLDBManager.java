package pe.edu.pucp.inf30.softprog.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author eric
 */
public class MSSQLDBManager extends DBManager {
    protected MSSQLDBManager() throws IOException {
        super();
    }
    
    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException  {
        try {
            Class.forName(
                    "com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String cadenaConexion = cadenaConexion(host, puerto, esquema);
            return DriverManager.getConnection(cadenaConexion, usuario, password);
        }
        catch (ClassNotFoundException | SQLException e) {
            System.err.println(e);
            throw e;
        }
    }
    
    @Override
    protected String cadenaConexion(String host, int puerto, String esquema) {
        return String.format("jdbc:sqlserver://%s:%d;databaseName=%s;encrypt=false", host, puerto, esquema);
    }
}
