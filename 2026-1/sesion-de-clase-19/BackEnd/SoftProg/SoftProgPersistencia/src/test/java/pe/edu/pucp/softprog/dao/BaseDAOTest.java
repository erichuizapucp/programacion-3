package pe.edu.pucp.softprog.dao;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseDAOTest {

    private static class Dummy {
        private int id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    private static class DummyDAO extends DefaultBaseDAO<Dummy> {
        PreparedStatement createStatement;
        PreparedStatement updateStatement;
        PreparedStatement deleteStatement;
        PreparedStatement readStatement;
        PreparedStatement readAllStatement;

        @Override
        protected PreparedStatement comandoCrear(Connection conn, Dummy modelo) {
            return createStatement;
        }

        @Override
        protected PreparedStatement comandoActualizar(Connection conn, Dummy modelo) {
            return updateStatement;
        }

        @Override
        protected PreparedStatement comandoEliminar(Connection conn, Integer id) {
            return deleteStatement;
        }

        @Override
        protected PreparedStatement comandoLeer(Connection conn, Integer id) {
            return readStatement;
        }

        @Override
        protected PreparedStatement comandoLeerTodos(Connection conn) {
            return readAllStatement;
        }

        @Override
        protected Dummy mapearModelo(ResultSet rs) throws java.sql.SQLException {
            Dummy d = new Dummy();
            d.setId(rs.getInt("id"));
            return d;
        }
    }

    @Test
    void crearDebeRetornarIdDeCallableStatement() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement callableStatement = mock(CallableStatement.class);

        when(manager.getConnection()).thenReturn(conn);
        when(callableStatement.executeUpdate()).thenReturn(1);
        when(callableStatement.getInt("p_id")).thenReturn(77);

        DummyDAO dao = new DummyDAO();
        dao.createStatement = callableStatement;

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            Integer id = dao.crear(new Dummy());
            assertEquals(77, id);
        }
    }

    @Test
    void leerTodosDebeMapearRegistrosConBaseDAO() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        PreparedStatement cmd = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(cmd.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("id")).thenReturn(1, 2);

        DummyDAO dao = new DummyDAO();
        dao.readAllStatement = cmd;

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            List<Dummy> lista = dao.leerTodos();
            assertEquals(2, lista.size());
            assertEquals(1, lista.get(0).getId());
            assertEquals(2, lista.get(1).getId());
        }
    }

    @Test
    void crearDebeRetornarNullSiNoSeInsertaRegistro() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        PreparedStatement cmd = mock(PreparedStatement.class);

        when(manager.getConnection()).thenReturn(conn);
        when(cmd.executeUpdate()).thenReturn(0);

        DummyDAO dao = new DummyDAO();
        dao.createStatement = cmd;

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertNull(dao.crear(new Dummy()));
        }
    }
}

