package pe.edu.pucp.softprog.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBManager {
    protected String servidor;
    protected int puerto;
    protected String esquema;
    protected String usuario;
    protected String password;

    private static DBManager instance;

    protected DBManager() {
    }

    protected DBManager(String servidor, int puerto, String esquema, String usuario, String password) {
        this.servidor = servidor;
        this.puerto = puerto;
        this.esquema = esquema;
        this.usuario = usuario;
        this.password = password;
    }

    public static synchronized DBManager getInstance() {
        if (instance == null) {
            ResourceBundle props = ResourceBundle.getBundle("db-mysql");

            String servidor = props.getString("servidor");
            int puerto = Integer.parseInt(props.getString("puerto"));
            String esquema = props.getString("esquema");
            String usuario = props.getString("usuario");
            String password = props.getString("password");

            instance = new DBManager(servidor, puerto, esquema, usuario, password);
        }
        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String cadenaConexion = String.format(
                "jdbc:mysql://%s:%d/%s?useSSL=false&allowPublicKeyRetrieval=true",
                servidor, puerto, esquema);
        return DriverManager.getConnection(cadenaConexion, usuario, password);
    }
}
