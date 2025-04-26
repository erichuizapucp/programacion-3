package pe.edu.pucp.inf30.softprog.test.rrhh;

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
import pe.edu.pucp.inf30.softprog.rrhh.dao.ICuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.rrhh.mysql.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.test.CrudDaoTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuentaUsuarioDaoTest implements CrudDaoTest {
    private int testId;
    private final int idIncorrecto = 99999;
    
    @Test
    @Order(1)
    @Override
    public void debeInsertar() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Usuario de prueba");
        cuentaUsuario.setPassword("facil");
        cuentaUsuario.setActivo(true);
        
        this.testId = cuentaUsuarioDao.insertar(cuentaUsuario);
        assertTrue(this.testId > 0);
    }
    
    @Test
    @Order(2)
    @Override
    public void debeModificarSiIdExiste() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setId(this.testId);
        cuentaUsuario.setUserName("Usuario de prueba modificado");
        cuentaUsuario.setPassword("dificil");
        cuentaUsuario.setActivo(false);
        
        boolean modifico = cuentaUsuarioDao.modificar(cuentaUsuario);
        assertTrue(modifico);
        
        CuentaUsuario cuentaUsuarioModificada = cuentaUsuarioDao.buscar(this.testId);
        assertEquals(cuentaUsuarioModificada.getUserName(), "Usuario de prueba modificado");
        assertEquals(cuentaUsuarioModificada.getPassword(), "dificil");
        assertFalse(cuentaUsuarioModificada.isActivo());
    }
    
    @Test
    @Order(3)
    @Override
    public void noDebeModificarSiIdNoExiste() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setId(this.idIncorrecto);
        cuentaUsuario.setUserName("Usuario de prueba modificado");
        cuentaUsuario.setPassword("dificil");
        cuentaUsuario.setActivo(false);
        
        boolean modifico = cuentaUsuarioDao.modificar(cuentaUsuario);
        assertFalse(modifico);
    }
    
    @Test
    @Order(4)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        boolean elimino = cuentaUsuarioDao.eliminar(this.idIncorrecto);
        assertFalse(elimino);
    }
    
    @Test
    @Order(5)
    @Override
    public void debeEncontrarSiIdExiste() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = cuentaUsuarioDao.buscar(this.testId);
        assertNotNull(cuentaUsuario);
    }
    
    @Test
    @Order(6)
    @Override
    public void noDebeEncontrarSiIdNoExiste() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = cuentaUsuarioDao.buscar(this.idIncorrecto);
        assertNull(cuentaUsuario);
    }
    
    @Test
    @Order(7)
    @Override
    public void debeListar() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        List<CuentaUsuario> cuentas = cuentaUsuarioDao.listar();
        
        assertNotNull(cuentas);
        assertFalse(cuentas.isEmpty());
    }
    
    @Test
    @Order(8)
    @Override
    public void debeEliminarSiIdExiste() {
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        boolean elimino = cuentaUsuarioDao.eliminar(this.testId);
        assertTrue(elimino);
    }
}
