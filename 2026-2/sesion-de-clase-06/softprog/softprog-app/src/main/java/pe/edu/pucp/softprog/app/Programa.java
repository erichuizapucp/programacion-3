package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.db.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Programa {
    public static void main(String[] args) throws SQLException {
        // Crear una instancia de DBManager utilizando el patrón Singleton
        try (Connection conn = DBManager.getInstance().getConnection()) {
            System.out.println("Conexión establecida con éxito a la base de datos.");
        }
    }
}
