package pe.edu.pucp.softprog.dao.rrhh;

import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.Genero;
import pe.edu.pucp.softprog.modelo.rrhh.Area;
import pe.edu.pucp.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class EmpleadoDAOImplTest {

    @Test
    void deberiaRetornarIdGeneradoCuandoCrearEsExitoso() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        PreparedStatement cmd = mock(PreparedStatement.class);
        ResultSet keys = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(cmd);
        when(cmd.executeUpdate()).thenReturn(1);
        when(cmd.getGeneratedKeys()).thenReturn(keys);
        when(keys.next()).thenReturn(true);
        when(keys.getInt(1)).thenReturn(21);

        Area area = new Area();
        area.setId(3);
        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setId(4);

        Empleado empleado = new Empleado();
        empleado.setArea(area);
        empleado.setCuentaUsuario(cuenta);
        empleado.setDni("87654321");
        empleado.setNombre("Luis");
        empleado.setApellidoPaterno("Diaz");
        empleado.setGenero(Genero.MASCULINO);
        empleado.setFechaNacimiento(Date.valueOf("1994-04-12"));
        empleado.setCargo(Cargo.ASISTENTE);
        empleado.setSueldo(2500);
        empleado.setActivo(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertEquals(21, new EmpleadoDAOImpl().crear(empleado));
        }
    }

    @Test
    void deberiaRetornarNullCuandoBuscaPorDniYNoExisteRegistro() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        PreparedStatement cmd = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString())).thenReturn(cmd);
        when(cmd.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertNull(new EmpleadoDAOImpl().buscarPorDni("87654321"));
        }
    }

    @Test
    void deberiaSetearNullEnCuentaUsuarioCuandoCrearSinCuenta() throws Exception {
        DBManager manager = mock(DBManager.class);
        Connection conn = mock(Connection.class);
        PreparedStatement cmd = mock(PreparedStatement.class);
        ResultSet keys = mock(ResultSet.class);

        when(manager.getConnection()).thenReturn(conn);
        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(cmd);
        when(cmd.executeUpdate()).thenReturn(1);
        when(cmd.getGeneratedKeys()).thenReturn(keys);
        when(keys.next()).thenReturn(true);
        when(keys.getInt(1)).thenReturn(22);

        Area area = new Area();
        area.setId(3);

        Empleado empleado = new Empleado();
        empleado.setArea(area);
        empleado.setCuentaUsuario(null);
        empleado.setDni("87654321");
        empleado.setNombre("Luis");
        empleado.setApellidoPaterno("Diaz");
        empleado.setGenero(Genero.MASCULINO);
        empleado.setFechaNacimiento(Date.valueOf("1994-04-12"));
        empleado.setCargo(Cargo.ASISTENTE);
        empleado.setSueldo(2500);
        empleado.setActivo(true);

        try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
            mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
            assertEquals(22, new EmpleadoDAOImpl().crear(empleado));
        }

        verify(cmd).setNull(2, java.sql.Types.INTEGER);
    }
}

