package pe.edu.pucp.softprog.dao.almacen;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ProductoDAOImplTest {

    @Test
    void deberiaRetornarIdGeneradoCuandoCrearEsExitoso() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement cmd = mock(CallableStatement.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareCall(anyString())).thenReturn(cmd);
        when(cmd.executeUpdate()).thenReturn(1);
        when(cmd.getInt("p_id")).thenReturn(30);

        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setUnidadMedida(UnidadMedida.UND);
        producto.setPrecio(5000);
        producto.setActivo(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertEquals(30, new ProductoDAOImpl().crear(producto));
        }
    }

    @Test
    void deberiaMapearCamposDeProductoCuandoLeerEncuentraUnaFila() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement cmd = mock(CallableStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareCall(anyString())).thenReturn(cmd);
        when(cmd.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt("id")).thenReturn(1);
        when(rs.getString("nombre")).thenReturn("Azucar");
        when(rs.getString("unidadMedida")).thenReturn("UND");
        when(rs.getDouble("precio")).thenReturn(9.5);
        when(rs.getBoolean("activo")).thenReturn(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            Producto producto = new ProductoDAOImpl().leer(1);
            assertNotNull(producto);
            assertEquals("Azucar", producto.getNombre());
            assertEquals(UnidadMedida.UND, producto.getUnidadMedida());
        }
    }
}

