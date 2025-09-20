package pe.edu.pucp.inf30.softprog.test.dao.clientes;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.clientes.ClienteDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Genero;
import pe.edu.pucp.inf30.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.test.dao.PersistibleProbable;

/**
 *
 * @author eric
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteDaoTest implements PersistibleProbable {
    private int testId;
    private int testCuentaUsuarioId;
    private final int idIncorrecto = 99999;
    
    @BeforeAll
    public void inicializar() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Cuenta de Prueba");
        cuentaUsuario.setPassword("Password de prueba");
        cuentaUsuario.setActivo(true);
        this.testCuentaUsuarioId = cuentaUsuarioDao.crear(cuentaUsuario);
    }
    
    @AfterAll
    public void limpiar() {
        CuentaUsuarioDAOImpl cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        cuentaUsuarioDao.eliminar(testCuentaUsuarioId);
    }
    
    @Test
    @Order(1)
    @Override
    public void debeCrear() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        Cliente cliente = new Cliente();
        cliente.setCuentaUsuario(
                new CuentaUsuarioDAOImpl().leer(this.testCuentaUsuarioId));
        cliente.setDni("12345678");
        cliente.setNombre("Nombre de prueba");
        cliente.setApellidoPaterno("Apellido de prueba");
        cliente.setGenero(Genero.FEMENINO);
        cliente.setCategoria(CategoriaCliente.ESTANDARD);
        cliente.setFechaNacimiento(new GregorianCalendar(1995, 
                    Calendar.JULY, 10).getTime());
        cliente.setLineaCredito(1000.00);
        cliente.setActivo(true);
        
        this.testId = clienteDao.crear(cliente);
        assertTrue(this.testId > 0);
    }
    
    @Test
    @Order(2)
    @Override
    public void debeActualizarSiIdExiste() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        Cliente cliente = new Cliente();
        cliente.setId(this.testId);
        cliente.setCuentaUsuario(
                new CuentaUsuarioDAOImpl().leer(this.testCuentaUsuarioId));
        cliente.setDni("87654321");
        cliente.setNombre("Nombre de prueba Modificado");
        cliente.setApellidoPaterno("Apellido de prueba Modificado");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setFechaNacimiento(new GregorianCalendar(2000, 
                Calendar.JULY, 28).getTime());
        cliente.setCategoria(CategoriaCliente.PREMIUM);
        cliente.setLineaCredito(1200.00);
        cliente.setActivo(false);

        boolean modifico = clienteDao.actualizar(cliente);
        assertTrue(modifico);

        Cliente clienteModificado = clienteDao.leer(this.testId);
        assertEquals(clienteModificado.getDni(), "87654321");
        assertEquals(clienteModificado.getNombre(), 
                "Nombre de prueba Modificado");
        assertEquals(clienteModificado.getApellidoPaterno(), 
                "Apellido de prueba Modificado");
        assertEquals(clienteModificado.getGenero(), Genero.MASCULINO);
        assertEquals(clienteModificado.getFechaNacimiento().getTime(), 
                new GregorianCalendar(2000, Calendar.JULY, 28).getTime()
                        .getTime());
        assertEquals(clienteModificado.getCategoria(), 
                CategoriaCliente.PREMIUM);
        assertEquals(clienteModificado.getLineaCredito(), 1200.00);
        assertFalse(clienteModificado.isActivo());
    }
    
    @Test
    @Order(3)
    @Override
    public void noDebeActualizarSiIdNoExiste() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        Cliente cliente = new Cliente();
        cliente.setId(this.idIncorrecto);
        cliente.setCuentaUsuario(
                new CuentaUsuarioDAOImpl().leer(this.testCuentaUsuarioId));
        cliente.setDni("87654321");
        cliente.setNombre("Nombre de prueba Modificado");
        cliente.setApellidoPaterno("Apellido de prueba Modificado");
        cliente.setGenero(Genero.MASCULINO);
        cliente.setCategoria(CategoriaCliente.PREMIUM);
        cliente.setFechaNacimiento(new GregorianCalendar(2002, 
            Calendar.JANUARY, 1).getTime());
        cliente.setLineaCredito(1200.00);
        cliente.setActivo(false);

        boolean modifico = clienteDao.actualizar(cliente);
        assertFalse(modifico);
    }
    
    @Test
    @Order(4)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        boolean elimino = clienteDao.eliminar(this.idIncorrecto);
        assertFalse(elimino);
    }
    
    @Test
    @Order(5)
    @Override
    public void debeLeerSiIdExiste() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        Cliente cliente = clienteDao.leer(this.testId);
        assertNotNull(cliente);
    }
    
    @Test
    @Order(6)
    @Override
    public void noDebeLeerSiIdNoExiste() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        Cliente cliente = clienteDao.leer(this.idIncorrecto);
        assertNull(cliente);
    }
    
    @Test
    @Order(7)
    @Override
    public void debeLeerTodos() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        List<Cliente> clientes = clienteDao.leerTodos();
        
        assertNotNull(clientes);
        assertFalse(clientes.isEmpty());
    }
    
    @Test
    @Order(8)
    @Override
    public void debeEliminarSiIdExiste() {
        ClienteDAO clienteDao = new ClienteDAOImpl();
        boolean elimino = clienteDao.eliminar(this.testId);
        assertTrue(elimino);
    }
}
