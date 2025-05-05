package pe.edu.pucp.inf30.softprog.test.dao.rrhh;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestInstance;
import pe.edu.pucp.inf30.softprog.dao.rrhh.IAreaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;
import pe.edu.pucp.inf30.softprog.test.dao.ICrudDaoTest;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AreaDaoTest implements ICrudDaoTest {
    private int testId;
    private final int idIncorrecto = 99999;
    
    @Test
    @Order(1)
    @Override
    public void debeInsertar() {
        IAreaDAO areaDao = new AreaDAOImpl();
        Area area = new Area();
        area.setNombre("Area de Prueba");
        area.setActivo(true);
        
        this.testId = areaDao.insertar(area);
        assertTrue(this.testId > 0);
    }
    
    @Test
    @Order(2)
    @Override
    public void debeModificarSiIdExiste() {
        IAreaDAO areaDao = new AreaDAOImpl();
        Area area = new Area();
        area.setId(this.testId);
        area.setNombre("Area de Prueba Modificada");
        area.setActivo(false);
        
        boolean modifico = areaDao.modificar(area);
        assertTrue(modifico);
        
        Area areaModificada = areaDao.buscar(this.testId);
        assertEquals(areaModificada.getNombre(), "Area de Prueba Modificada");
        assertFalse(areaModificada.isActivo());
    }
    
    @Test
    @Order(3)
    @Override
    public void noDebeModificarSiIdNoExiste() {
        IAreaDAO areaDao = new AreaDAOImpl();
        Area area = new Area();
        area.setId(this.idIncorrecto);
        area.setNombre("Area de Prueba Modificada");
        area.setActivo(false);
        
        boolean modifico = areaDao.modificar(area);
        assertFalse(modifico);
    }
    
    @Test
    @Order(4)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        IAreaDAO areaDao = new AreaDAOImpl();
        boolean elimino = areaDao.eliminar(this.idIncorrecto);
        assertFalse(elimino);
    }
    
    @Test
    @Order(5)
    @Override
    public void debeEncontrarSiIdExiste() {
        IAreaDAO areaDao = new AreaDAOImpl();
        Area area = areaDao.buscar(this.testId);
        assertNotNull(area);
    }
    
    @Test
    @Order(6)
    @Override
    public void noDebeEncontrarSiIdNoExiste() {
        IAreaDAO areaDao = new AreaDAOImpl();
        Area area = areaDao.buscar(this.idIncorrecto);
        assertNull(area);
    }
    
    @Test
    @Order(7)
    @Override
    public void debeListar() {
        IAreaDAO areaDao = new AreaDAOImpl();
        List<Area> areas = areaDao.listar();
        
        assertNotNull(areas);
        assertFalse(areas.isEmpty());
    }
    
    @Test
    @Order(8)
    @Override
    public void debeEliminarSiIdExiste() {
        IAreaDAO areaDao = new AreaDAOImpl();
        boolean elimino = areaDao.eliminar(this.testId);
        assertTrue(elimino);
    }
}
