package pe.edu.pucp.softprog.dao.cuentas;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class CuentaUsuarioDAOImplTest {

    @Test
    void deberiaRetornarTrueCuandoCredencialesDeLoginSonValidas() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        PreparedStatement cmd = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(cmd);
        when(cmd.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            boolean ok = new CuentaUsuarioDAOImpl().login("user", "123456");
            assertTrue(ok);
        }
    }

    @Test
    void deberiaRetornarFalseCuandoActualizarNoAfectaFilas() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        PreparedStatement cmd = mock(PreparedStatement.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(cmd);
        when(cmd.executeUpdate()).thenReturn(0);

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setId(1);
        cuenta.setUserName("u1");
        cuenta.setPassword("p1");
        cuenta.setActivo(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertFalse(new CuentaUsuarioDAOImpl().actualizar(cuenta));
        }
    }
}

