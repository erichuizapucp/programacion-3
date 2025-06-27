package pe.edu.pucp.inf30.softprog.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DBManager {
    private static DBManager dbManager;
    
    protected String host;
    protected int puerto;
    protected String esquema;
    protected String usuario;
    protected String password;
    protected TipoDB tipo;
    
    protected DBManager() throws IOException {
        cargarProperties();
    }
    
    public synchronized static DBManager getInstance() throws IOException {
        if (dbManager == null) {
            createInstance();
        }
        return dbManager;
    }
    
    public TipoDB getTipo() {
        return tipo;
    }
    
    private static void createInstance() throws IOException {
        Properties properties = new Properties();
        try (InputStream input = DBManager.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new IOException("No se pudo abrir el archivo db.properties");
            }
            properties.load(input);

            TipoDB tipo = TipoDB.valueOf(properties.getProperty("db.tipo"));
            switch(tipo) {
                case MySQL -> dbManager = new MySQLDBManager();
                case MSSQL -> dbManager = new MSSQLDBManager();
            }
        }
    }
    
    public abstract Connection getConnection() throws SQLException, ClassNotFoundException;
    
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
            tipo = TipoDB.valueOf(properties.getProperty("db.tipo"));
        }
        catch (IOException e) {
            System.err.println("No se pudo cargar el archivo db.properties");
            throw e;
        }
    }
    
    protected abstract String cadenaConexion(String host, int puerto, String esquema);
}
