package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.db.DBManager;

public class Programa {
    public static void main(String[] args) {
        // Crear una instancia de DBManager utilizando el patrón Singleton
        DBManager db = DBManager.getInstance();
    }
}
