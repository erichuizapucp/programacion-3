package pe.edu.pucp.softprog.dao.clientes;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ClienteDAOImplTest {

    @Test
    void deberiaRetornarIdGeneradoCuandoCrearEsExitoso() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement cmd = mock(CallableStatement.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareCall(anyString())).thenReturn(cmd);
        when(cmd.executeUpdate()).thenReturn(1);
        when(cmd.getInt("p_id")).thenReturn(11);

        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setId(5);

        Cliente cliente = new Cliente();
        cliente.setCuentaUsuario(cuenta);
        cliente.setDni("12345678");
        cliente.setNombre("Ana");
        cliente.setApellidoPaterno("Lopez");
        cliente.setGenero(Genero.FEMENINO);
        cliente.setFechaNacimiento(Date.valueOf("1998-05-20"));
        cliente.setCategoria(CategoriaCliente.ESTANDARD);
        cliente.setLineaCredito(1000);
        cliente.setActivo(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertEquals(11, new ClienteDAOImpl().crear(cliente));
        }
    }

    @Test
    void deberiaRetornarNullCuandoBuscaPorDniYNoExisteRegistro() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement cmd = mock(CallableStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareCall(anyString())).thenReturn(cmd);
        when(cmd.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertNull(new ClienteDAOImpl().buscarPorDni("12345678"));
        }
    }

    @Test
    void deberiaSetearNullEnCuentaUsuarioCuandoCrearSinCuenta() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        CallableStatement cmd = mock(CallableStatement.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareCall(anyString())).thenReturn(cmd);
        when(cmd.executeUpdate()).thenReturn(1);
        when(cmd.getInt("p_id")).thenReturn(12);

        Cliente cliente = new Cliente();
        cliente.setCuentaUsuario(null);
        cliente.setDni("12345678");
        cliente.setNombre("Ana");
        cliente.setApellidoPaterno("Lopez");
        cliente.setGenero(Genero.FEMENINO);
        cliente.setFechaNacimiento(Date.valueOf("1998-05-20"));
        cliente.setCategoria(CategoriaCliente.ESTANDARD);
        cliente.setLineaCredito(1000);
        cliente.setActivo(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertEquals(12, new ClienteDAOImpl().crear(cliente));
        }

        verify(cmd).setNull("p_idCuentaUsuario", java.sql.Types.INTEGER);
    }
}

