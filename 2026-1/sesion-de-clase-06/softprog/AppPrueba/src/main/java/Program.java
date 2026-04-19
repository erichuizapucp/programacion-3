import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Program {
    public static void main(String[] args) {
        ResourceBundle db = ResourceBundle.getBundle("db");

        String host = db.getString("db.host");
        int port = Integer.parseInt(db.getString("db.puerto"));
        String esquema = db.getString("db.esquema");
        String usuario = db.getString("db.usuario");
        String password = db.getString("db.password");

        String url = "jdbc:mysql://" + host + ":" + port + "/" + esquema;

        try (Connection connection =
                     DriverManager.getConnection(url, usuario, password)) {
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexión con la base de datos exitosa!");
            }
        } catch (SQLException e) {
            System.err.println("Error de conectividad JDBC:");
        }
    }
}
