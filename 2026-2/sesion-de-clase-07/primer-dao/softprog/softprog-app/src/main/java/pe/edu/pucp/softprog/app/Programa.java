package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.db.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

public class Programa {

    public static void main(String[] args) {
        try (Connection conn = DBManager.getInstance().getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("Conexion establecida con exito.");
                System.out.println("Esquema: " + conn.getCatalog());
                System.out.println("Driver:  " + conn.getMetaData().getDriverName());
            } else {
                System.out.println("No se pudo establecer la conexion.");
            }
        } catch (SQLException ex) {
            System.out.println("Error al conectar: " + ex.getMessage());
        }
    }
}
