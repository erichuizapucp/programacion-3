package pe.edu.pucp.softprog.dao.rrhh;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AreaDAOImplTest {

    @Test
    void deberiaRetornarIdGeneradoCuandoCrearEsExitoso() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement cmd = mock(CallableStatement.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareCall(anyString())).thenReturn(cmd);
        when(cmd.executeUpdate()).thenReturn(1);
        when(cmd.getInt("p_id")).thenReturn(10);

        Area area = new Area();
        area.setNombre("RRHH");
        area.setActivo(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            Integer id = new AreaDAOImpl().crear(area);
            assertEquals(10, id);
        }
    }

    @Test
    void deberiaMapearTodasLasFilasCuandoLeerTodosEsEjecutado() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement cmd = mock(CallableStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareCall(anyString())).thenReturn(cmd);
        when(cmd.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getInt(1)).thenReturn(1);
        when(rs.getString(2)).thenReturn("Area 1");
        when(rs.getBoolean(3)).thenReturn(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            List<Area> areas = new AreaDAOImpl().leerTodos();
            assertEquals(1, areas.size());
            assertEquals("Area 1", areas.get(0).getNombre());
        }
    }
}

