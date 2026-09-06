package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.db.DBManager;

import java.sql.Connection;

public class Program {
    public static void main(String[] args) {
        try {
            DBManager dbManager = DBManager.getInstance();
            try (Connection connection = dbManager.getConnection()) {
                System.out.println("Conexión a la base de datos exitosa.");
            }
        } catch (Exception e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
}
