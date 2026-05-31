package pe.edu.pucp.softprog.dao;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mockStatic;

class TransactionsManagerTest {

    @Test
    void iniciarYCommitDebeManejarConexionDelThreadLocal() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        when(manager.getConnection()).thenReturn(conn);
        when(conn.getAutoCommit()).thenReturn(false);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);

            TransactionsManager.iniciarTransaccion();
            assertSame(conn, TransactionsManager.obtenerConexionActual());

            TransactionsManager.commitTransaccion();
            assertFalse(TransactionsManager.hayTransaccionActiva());
        }

        verify(conn).setAutoCommit(false);
        verify(conn).commit();
        verify(conn).setAutoCommit(true);
        verify(conn).close();
    }

    @Test
    void rollbackSinTransaccionActivaDebeLanzarError() {
        assertThrows(IllegalStateException.class, TransactionsManager::rollbackTransaccion);
    }
}

