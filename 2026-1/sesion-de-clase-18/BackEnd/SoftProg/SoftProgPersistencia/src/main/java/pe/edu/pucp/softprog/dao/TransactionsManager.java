package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

public final class TransactionsManager {
    private static final ThreadLocal<Connection> TX_CONNECTION = new ThreadLocal<>();

    private TransactionsManager() {
    }

    public static void iniciarTransaccion() {
        if (hayTransaccionActiva()) {
            throw new IllegalStateException("Ya existe una transaccion activa en este hilo");
        }

        DBManager dbManager = DBFactoryProvider.getManager();
        try {
            Connection connection = dbManager.getConnection();
            connection.setAutoCommit(false);
            TX_CONNECTION.set(connection);
        }
        catch (Exception ex) {
            throw new RuntimeException("No se pudo iniciar la transaccion", ex);
        }
    }

    public static void commitTransaccion() {
        Connection connection = obtenerConexionActiva();
        try {
            connection.commit();
        }
        catch (SQLException ex) {
            throw new RuntimeException("No se pudo confirmar la transaccion", ex);
        }
        finally {
            cerrarConexion(connection);
            TX_CONNECTION.remove();
        }
    }

    public static void rollbackTransaccion() {
        Connection connection = obtenerConexionActiva();
        try {
            connection.rollback();
        }
        catch (SQLException ex) {
            throw new RuntimeException("No se pudo revertir la transaccion", ex);
        }
        finally {
            cerrarConexion(connection);
            TX_CONNECTION.remove();
        }
    }

    public static Connection obtenerConexionActual() {
        return TX_CONNECTION.get();
    }

    public static boolean hayTransaccionActiva() {
        return TX_CONNECTION.get() != null;
    }

    private static Connection obtenerConexionActiva() {
        Connection connection = TX_CONNECTION.get();
        if (connection == null) {
            throw new IllegalStateException("No hay una transaccion activa en este hilo");
        }
        return connection;
    }

    private static void cerrarConexion(Connection connection) {
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
            connection.close();
        }
        catch (SQLException ex) {
            throw new RuntimeException("No se pudo cerrar la conexion de la transaccion", ex);
        }
    }
}

