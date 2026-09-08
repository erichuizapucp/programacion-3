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

    protected DBManager(String servidor,
                        int puerto,
                        String esquema,
                        String usuario,
                        String password) {

        this.servidor = servidor;
        this.puerto = puerto;
        this.esquema = esquema;
        this.usuario = usuario;
        this.password = password;
    }

    public synchronized static DBManager getInstance() {
        if (instance == null) {
            ResourceBundle rb = ResourceBundle.getBundle("db");

            instance = new DBManager(
                rb.getString("servidor"),
                Integer.parseInt(rb.getString("puerto")),
                rb.getString("esquema"),
                rb.getString("usuario"),
                rb.getString("password")
            );
        }
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://" + servidor + ":" + puerto + "/" + esquema,
            usuario,
            password
        );
    }
}
