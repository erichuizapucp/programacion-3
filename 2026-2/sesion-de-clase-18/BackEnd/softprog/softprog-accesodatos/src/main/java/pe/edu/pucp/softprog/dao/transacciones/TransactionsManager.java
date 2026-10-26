package pe.edu.pucp.softprog.dao.transacciones;

import pe.edu.pucp.softprog.db.DBManager;

import java.sql.Connection;
import java.sql.SQLException;

public final class TransactionsManager {
    private static final ThreadLocal<Connection> conexion = new ThreadLocal<>();

    private TransactionsManager() {
    }

    public static void iniciar() {
        if (activa()) {
            throw new IllegalStateException("Ya existe una transaccion activa en este hilo");
        }

        Connection conn = null;
        try {
            conn = DBManager.getInstance().getConnection();
            conn.setAutoCommit(false);
            conexion.set(conn);
        } catch (SQLException ex) {
            if (conn != null) {
                try { conn.close(); } catch (SQLException ignored) { }
            }
            throw new RuntimeException("No se pudo iniciar la transaccion", ex);
        }
    }

    public static void commit() {
        Connection conn = getConnection();
        try {
            conn.commit();
        } catch (SQLException ex) {
            try { conn.rollback(); } catch (SQLException ignored) { }
            throw new RuntimeException("No se pudo confirmar la transaccion", ex);
        } finally {
            cerrar(conn);
        }
    }

    public static void rollback() {
        Connection conn = getConnection();
        try {
            conn.rollback();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo revertir la transaccion", ex);
        } finally {
            cerrar(conn);
        }
    }

    public static Connection getConnection() {
        Connection conn = conexion.get();
        if (conn == null) {
            throw new IllegalStateException("No hay una transaccion activa en este hilo");
        }
        return conn;
    }

    public static boolean activa() {
        return conexion.get() != null;
    }

    private static void cerrar(Connection conn) {
        conexion.remove();
        try {
            conn.setAutoCommit(true);
            conn.close();
        } catch (SQLException ex) {
            throw new RuntimeException("No se pudo cerrar la conexion", ex);
        }
    }
}