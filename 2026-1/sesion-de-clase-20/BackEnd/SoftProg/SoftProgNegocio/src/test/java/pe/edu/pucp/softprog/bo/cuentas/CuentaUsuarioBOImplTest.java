package pe.edu.pucp.softprog.bo.cuentas;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class CuentaUsuarioBOImplTest {
    @Test
    void loginDebeValidarUsernameObligatorio() {
        try (MockedConstruction<CuentaUsuarioDAOImpl> mocked = mockConstruction(CuentaUsuarioDAOImpl.class)) {
            CuentaUsuarioBOImpl bo = new CuentaUsuarioBOImpl();

            assertThrows(IllegalArgumentException.class, () -> bo.login(" ", "123456"));
            CuentaUsuarioDAOImpl daoMock = mocked.constructed().getFirst();
            verifyNoInteractions(daoMock);
        }
    }

    @Test
    void loginDebeDelegarEnDao() {
        try (MockedConstruction<CuentaUsuarioDAOImpl> mocked = mockConstruction(CuentaUsuarioDAOImpl.class,
                (dao, ignored) -> when(dao.login("ana", "123456")).thenReturn(false))) {
            CuentaUsuarioBOImpl bo = new CuentaUsuarioBOImpl();

            assertFalse(bo.login("ana", "123456"));
            CuentaUsuarioDAOImpl daoMock = mocked.constructed().getFirst();
            verify(daoMock).login("ana", "123456");
        }
    }

    @Test
    void guardarModificadoDebeFallarCuandoDaoNoActualiza() {
        try (MockedConstruction<CuentaUsuarioDAOImpl> mocked = mockConstruction(CuentaUsuarioDAOImpl.class,
                (dao, ignored) -> when(dao.actualizar(any())).thenReturn(false))) {
            CuentaUsuarioBOImpl bo = new CuentaUsuarioBOImpl();
            CuentaUsuario cuenta = crearCuentaValida();
            cuenta.setId(4);

            assertThrows(IllegalStateException.class, () -> bo.guardar(cuenta, Estado.Modificado));
            CuentaUsuarioDAOImpl daoMock = mocked.constructed().getFirst();
            verify(daoMock).actualizar(cuenta);
        }
    }

    private CuentaUsuario crearCuentaValida() {
        CuentaUsuario cuenta = new CuentaUsuario();
        cuenta.setUserName("ana");
        cuenta.setPassword("123456");
        cuenta.setActivo(true);
        return cuenta;
    }
}


