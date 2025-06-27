package pe.edu.pucp.inf30.softprog.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author eric
 */
public class MySQLDBManager extends DBManager {
    protected MySQLDBManager() throws IOException {
        super();
    }
    
    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            /* 
            Por ahora creamos una conexion cada vez que se necesita acceder a la base de datos, 
            por ser una aplicacion academica es una practica aceptable, en un sistema productivo
            se debe usar un pool de conexiones.
            */
            Class.forName("com.mysql.cj.jdbc.Driver");
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
        return String.format("jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true", host, puerto, esquema);
    }
}
