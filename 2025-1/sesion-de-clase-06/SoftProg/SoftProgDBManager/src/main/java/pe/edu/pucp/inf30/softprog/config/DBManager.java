package pe.edu.pucp.inf30.softprog.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    private static DBManager dbManager;
    private Connection con;
    private ResultSet rs;
    
    private String host;
    private int puerto;
    private String esquema;
    private String usuario;
    private String password;
    
    private DBManager() throws IOException {
        cargarProperties();
    }
    
    public synchronized static DBManager getInstance() throws IOException {
        if (dbManager == null) {
            createInstance();
        }
        return dbManager;
    }
    
    private static void createInstance() throws IOException {
        dbManager = new DBManager();
    }
    
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String cadenaConexion = cadenaConexion(host, puerto, esquema);
                con = DriverManager.getConnection(cadenaConexion, usuario, password);
            }
            catch (ClassNotFoundException | SQLException e) {
                System.err.println(e);
                throw e;
            }
        }
        
        return con;
    }
    
    public void cerrarConexion() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        
        if (con != null) {
            con.close();
        }
    }
    
    private void cargarProperties() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                System.err.println("No se pudo abrir el archivo db.properties");
                return;
            }
            
            properties.load(input);
            
            host = properties.getProperty("db.host");
            puerto = Integer.parseInt(properties.getProperty("db.puerto"));
            esquema = properties.getProperty("db.esquema");
            usuario = properties.getProperty("db.usuario");
            password = properties.getProperty("db.password");
        }
        catch (IOException e) {
            System.err.println("No se pudo cargar el archivo db.properties");
            throw e;
        }
    }
    
    private String cadenaConexion(String host, int puerto, String esquema) {
        return String.format("jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true", host, puerto, esquema);
    }
}
