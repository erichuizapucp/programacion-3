package pe.edu.pucp.inf30.softprog.test.rrhh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IAreaDAO;
import pe.edu.pucp.inf30.softprog.rrhh.dao.ICuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IEmpleadoDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;
import pe.edu.pucp.inf30.softprog.rrhh.model.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.rrhh.model.Empleado;
import pe.edu.pucp.inf30.softprog.rrhh.mysql.AreaDAOImpl;
import pe.edu.pucp.inf30.softprog.rrhh.mysql.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.rrhh.mysql.EmpleadoDAOImpl;
import pe.edu.pucp.inf30.softprog.test.ICrudDaoTest;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmpleadoDaoTest implements ICrudDaoTest {
    private int testId;
    private int testAreaId;
    private int testCuentaUsuarioId;
    private final int idIncorrecto = 99999;
    
    @BeforeAll
    public void inicializar() {
        IAreaDAO areaDao = new AreaDAOImpl();
        Area area = new Area();
        area.setNombre("Area de Prueba");
        area.setActivo(true);
        this.testAreaId = areaDao.insertar(area);
        
        ICuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Cuenta de Prueba");
        cuentaUsuario.setPassword("Password de prueba");
        cuentaUsuario.setActivo(true);
        this.testCuentaUsuarioId = cuentaUsuarioDao.insertar(cuentaUsuario);
    }
    
    @AfterAll
    public void limpiar() {
        AreaDAOImpl areaDao = new AreaDAOImpl();
        areaDao.eliminar(testAreaId);
        
        CuentaUsuarioDAOImpl cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        cuentaUsuarioDao.eliminar(testCuentaUsuarioId);
    }
    
    @Test
    @Order(1)
    @Override
    public void debeInsertar() {
        IEmpleadoDAO empladoDao = new EmpleadoDAOImpl();
        Empleado empleado = new Empleado();
        empleado.setArea(new AreaDAOImpl().buscar(this.testAreaId));
        empleado.setCuentaUsuario(new CuentaUsuarioDAOImpl().buscar(this.testCuentaUsuarioId));
        empleado.setDni("12345678");
        empleado.setNombre("Nombre de prueba");
        empleado.setApellidoPaterno("Apellido de prueba");
        empleado.setGenero('F');
        try {
            empleado.setFechaNacimiento(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2000"));
        }
        catch (ParseException e) {
            System.err.println("No se puede inicializar la fecha" + e.getMessage());
        }
        
        empleado.setCargo("Cargo de prueba");
        empleado.setSueldo(1000.00);
        empleado.setActivo(true);
        
        this.testId = empladoDao.insertar(empleado);
        assertTrue(this.testId > 0);
    }
    
    @Test
    @Order(2)
    @Override
    public void debeModificarSiIdExiste() {
        try {
            IEmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
            Empleado empleado = new Empleado();
            empleado.setId(this.testId);
            empleado.setArea(new AreaDAOImpl().buscar(this.testAreaId));
            empleado.setCuentaUsuario(new CuentaUsuarioDAOImpl().buscar(this.testCuentaUsuarioId));
            empleado.setDni("87654321");
            empleado.setNombre("Nombre de prueba Modificado");
            empleado.setApellidoPaterno("Apellido de prueba Modificado");
            empleado.setGenero('M');
            empleado.setFechaNacimiento(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2002"));
            empleado.setCargo("Cargo de prueba Modificado");
            empleado.setSueldo(1200.00);
            empleado.setActivo(false);

            boolean modifico = empleadoDao.modificar(empleado);
            assertTrue(modifico);

            Empleado empleadoModificado = empleadoDao.buscar(this.testId);
            assertEquals(empleadoModificado.getDni(), "87654321");
            assertEquals(empleadoModificado.getNombre(), "Nombre de prueba Modificado");
            assertEquals(empleadoModificado.getApellidoPaterno(), "Apellido de prueba Modificado");
            assertEquals(empleadoModificado.getGenero(), 'M');
            assertEquals(empleadoModificado.getFechaNacimiento(), new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2002"));
            assertEquals(empleadoModificado.getCargo(), "Cargo de prueba Modificado");
            assertEquals(empleadoModificado.getSueldo(), 1200.00);
            assertFalse(empleadoModificado.isActivo());
        }
        catch (ParseException e) {
            System.err.println("No se puede inicializar la fecha" + e.getMessage());
            throw new RuntimeException("No se puede incializar la fecha", e);
        }
    }
    
    @Test
    @Order(3)
    @Override
    public void noDebeModificarSiIdNoExiste() {
        try {
            IEmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
            Empleado empleado = new Empleado();
            empleado.setId(this.idIncorrecto);
            empleado.setArea(new AreaDAOImpl().buscar(this.testAreaId));
            empleado.setCuentaUsuario(new CuentaUsuarioDAOImpl().buscar(this.testCuentaUsuarioId));
            empleado.setDni("87654321");
            empleado.setNombre("Nombre de prueba Modificado");
            empleado.setApellidoPaterno("Apellido de prueba Modificado");
            empleado.setGenero('M');
            empleado.setFechaNacimiento(new SimpleDateFormat("dd-MM-yyyy").parse("01-01-2002"));
            empleado.setCargo("Cargo de prueba Modificado");
            empleado.setSueldo(1200.00);
            empleado.setActivo(false);

            boolean modifico = empleadoDao.modificar(empleado);
            assertFalse(modifico);
        }
        catch (ParseException e) {
            System.err.println("No se puede inicializar la fecha" + e.getMessage());
            throw new RuntimeException("No se puede incializar la fecha", e);
        }
    }
    
    @Test
    @Order(4)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        IEmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        boolean elimino = empleadoDao.eliminar(this.idIncorrecto);
        assertFalse(elimino);
    }
    
    @Test
    @Order(5)
    @Override
    public void debeEncontrarSiIdExiste() {
        IEmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        Empleado empleado = empleadoDao.buscar(this.testId);
        assertNotNull(empleado);
    }
    
    @Test
    @Order(6)
    @Override
    public void noDebeEncontrarSiIdNoExiste() {
        IEmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        Empleado empleado = empleadoDao.buscar(this.idIncorrecto);
        assertNull(empleado);
    }
    
    @Test
    @Order(7)
    @Override
    public void debeListar() {
        IEmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        List<Empleado> empleados = empleadoDao.listar();
        
        assertNotNull(empleados);
        assertFalse(empleados.isEmpty());
    }
    
    @Test
    @Order(8)
    @Override
    public void debeEliminarSiIdExiste() {
        IEmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        boolean elimino = empleadoDao.eliminar(this.testId);
        assertTrue(elimino);
    }
}
