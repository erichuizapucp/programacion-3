package pe.edu.pucp.inf30.softprog.test.dao.rrhh;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.test.dao.PersistibleProbable;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaUsuarioDaoTest implements PersistibleProbable {
    private int testId;
    private final int idIncorrecto = 99999;
    
    @Test
    @Order(1)
    @Override
    public void debeCrear() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Usuario de prueba");
        cuentaUsuario.setPassword("facil");
        cuentaUsuario.setActivo(true);
        
        this.testId = cuentaUsuarioDao.crear(cuentaUsuario);
        assertTrue(this.testId > 0);
    }
    
    @Test
    @Order(2)
    @Override
    public void debeActualizarSiIdExiste() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setId(this.testId);
        cuentaUsuario.setUserName("Usuario de prueba modificado");
        cuentaUsuario.setPassword("dificil");
        cuentaUsuario.setActivo(false);
        
        boolean modifico = cuentaUsuarioDao.actualizar(cuentaUsuario);
        assertTrue(modifico);
        
        CuentaUsuario cuentaUsuarioModificada = cuentaUsuarioDao.leer(this.testId);
        assertEquals(cuentaUsuarioModificada.getUserName(), "Usuario de prueba modificado");
        assertEquals(cuentaUsuarioModificada.getPassword(), "dificil");
        assertFalse(cuentaUsuarioModificada.isActivo());
    }
    
    @Test
    @Order(3)
    @Override
    public void noDebeActualizarSiIdNoExiste() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setId(this.idIncorrecto);
        cuentaUsuario.setUserName("Usuario de prueba modificado");
        cuentaUsuario.setPassword("dificil");
        cuentaUsuario.setActivo(false);
        
        boolean modifico = cuentaUsuarioDao.actualizar(cuentaUsuario);
        assertFalse(modifico);
    }
    
    @Test
    @Order(4)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        boolean elimino = cuentaUsuarioDao.eliminar(this.idIncorrecto);
        assertFalse(elimino);
    }
    
    @Test
    @Order(5)
    @Override
    public void debeLeerSiIdExiste() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = cuentaUsuarioDao.leer(this.testId);
        assertNotNull(cuentaUsuario);
    }
    
    @Test
    @Order(6)
    @Override
    public void noDebeLeerSiIdNoExiste() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = cuentaUsuarioDao.leer(this.idIncorrecto);
        assertNull(cuentaUsuario);
    }
    
    @Test
    @Order(7)
    @Override
    public void debeLeerTodos() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        List<CuentaUsuario> cuentas = cuentaUsuarioDao.leerTodos();
        
        assertNotNull(cuentas);
        assertFalse(cuentas.isEmpty());
    }
    
    @Test
    @Order(8)
    @Override
    public void debeEliminarSiIdExiste() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        boolean elimino = cuentaUsuarioDao.eliminar(this.testId);
        assertTrue(elimino);
    }
}
