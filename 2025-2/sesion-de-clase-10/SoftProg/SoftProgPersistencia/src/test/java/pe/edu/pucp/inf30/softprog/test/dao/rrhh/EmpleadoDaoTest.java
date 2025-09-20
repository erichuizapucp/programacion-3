package pe.edu.pucp.inf30.softprog.test.dao.rrhh;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
import pe.edu.pucp.inf30.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.EmpleadoDAO;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Genero;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.inf30.softprog.test.dao.PersistibleProbable;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmpleadoDaoTest implements PersistibleProbable {
    private int testId;
    private int testAreaId;
    private int testCuentaUsuarioId;
    private final int idIncorrecto = 99999;
    
    @BeforeAll
    public void inicializar() {
        AreaDAO areaDao = new AreaDAOImpl();
        Area area = new Area();
        area.setNombre("Area de Prueba");
        area.setActivo(true);
        this.testAreaId = areaDao.crear(area);
        
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Cuenta de Prueba");
        cuentaUsuario.setPassword("Password de prueba");
        cuentaUsuario.setActivo(true);
        this.testCuentaUsuarioId = cuentaUsuarioDao.crear(cuentaUsuario);
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
    public void debeCrear() {
        EmpleadoDAO empladoDao = new EmpleadoDAOImpl();
        Empleado empleado = new Empleado();
        empleado.setArea(new AreaDAOImpl().leer(this.testAreaId));
        empleado.setCuentaUsuario(
                new CuentaUsuarioDAOImpl().leer(this.testCuentaUsuarioId));
        empleado.setDni("12345678");
        empleado.setNombre("Nombre de prueba");
        empleado.setApellidoPaterno("Apellido de prueba");
        empleado.setGenero(Genero.FEMENINO);
        empleado.setFechaNacimiento(new GregorianCalendar(1995, 
                    Calendar.JULY, 10).getTime());
        
        empleado.setCargo(Cargo.ASISTENTE);
        empleado.setSueldo(1000.00);
        empleado.setActivo(true);
        
        this.testId = empladoDao.crear(empleado);
        assertTrue(this.testId > 0);
    }
    
    @Test
    @Order(2)
    @Override
    public void debeActualizarSiIdExiste() {
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        Empleado empleado = new Empleado();
        empleado.setId(this.testId);
        empleado.setArea(new AreaDAOImpl().leer(this.testAreaId));
        empleado.setCuentaUsuario(
                new CuentaUsuarioDAOImpl().leer(this.testCuentaUsuarioId));
        empleado.setDni("87654321");
        empleado.setNombre("Nombre de prueba Modificado");
        empleado.setApellidoPaterno("Apellido de prueba Modificado");
        empleado.setGenero(Genero.MASCULINO);
        empleado.setFechaNacimiento(new GregorianCalendar(2000, 
                Calendar.JULY, 28).getTime());
        empleado.setCargo(Cargo.TECNICO);
        empleado.setSueldo(1200.00);
        empleado.setActivo(false);

        boolean modifico = empleadoDao.actualizar(empleado);
        assertTrue(modifico);

        Empleado empleadoModificado = empleadoDao.leer(this.testId);
        assertEquals(empleadoModificado.getDni(), "87654321");
        assertEquals(empleadoModificado.getNombre(), 
                "Nombre de prueba Modificado");
        assertEquals(empleadoModificado.getApellidoPaterno(), 
                "Apellido de prueba Modificado");
        assertEquals(empleadoModificado.getGenero(), Genero.MASCULINO);
        assertEquals(empleadoModificado.getFechaNacimiento().getTime(), 
                new GregorianCalendar(2000, Calendar.JULY, 28).getTime()
                        .getTime());
        assertEquals(empleadoModificado.getCargo(), Cargo.TECNICO);
        assertEquals(empleadoModificado.getSueldo(), 1200.00);
        assertFalse(empleadoModificado.isActivo());
    }
    
    @Test
    @Order(3)
    @Override
    public void noDebeActualizarSiIdNoExiste() {
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        Empleado empleado = new Empleado();
        empleado.setId(this.idIncorrecto);
        empleado.setArea(new AreaDAOImpl().leer(this.testAreaId));
        empleado.setCuentaUsuario(
                new CuentaUsuarioDAOImpl().leer(this.testCuentaUsuarioId));
        empleado.setDni("87654321");
        empleado.setNombre("Nombre de prueba Modificado");
        empleado.setApellidoPaterno("Apellido de prueba Modificado");
        empleado.setGenero(Genero.MASCULINO);
        empleado.setFechaNacimiento(new GregorianCalendar(2002, 
            Calendar.JANUARY, 1).getTime());
        empleado.setCargo(Cargo.TECNICO);
        empleado.setSueldo(1200.00);
        empleado.setActivo(false);

        boolean modifico = empleadoDao.actualizar(empleado);
        assertFalse(modifico);
    }
    
    @Test
    @Order(4)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        boolean elimino = empleadoDao.eliminar(this.idIncorrecto);
        assertFalse(elimino);
    }
    
    @Test
    @Order(5)
    @Override
    public void debeLeerSiIdExiste() {
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        Empleado empleado = empleadoDao.leer(this.testId);
        assertNotNull(empleado);
    }
    
    @Test
    @Order(6)
    @Override
    public void noDebeLeerSiIdNoExiste() {
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        Empleado empleado = empleadoDao.leer(this.idIncorrecto);
        assertNull(empleado);
    }
    
    @Test
    @Order(7)
    @Override
    public void debeLeerTodos() {
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        List<Empleado> empleados = empleadoDao.leerTodos();
        
        assertNotNull(empleados);
        assertFalse(empleados.isEmpty());
    }
    
    @Test
    @Order(8)
    @Override
    public void debeEliminarSiIdExiste() {
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        boolean elimino = empleadoDao.eliminar(this.testId);
        assertTrue(elimino);
    }
}
