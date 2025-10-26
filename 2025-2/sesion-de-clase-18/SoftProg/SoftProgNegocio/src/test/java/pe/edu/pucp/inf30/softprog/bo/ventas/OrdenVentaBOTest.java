package pe.edu.pucp.inf30.softprog.bo.ventas;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;

import static org.junit.jupiter.api.Assertions.*;
import pe.edu.pucp.inf30.softprog.bo.GestionableProbable;

import pe.edu.pucp.inf30.softprog.boimpl.ventas.OrdenVentaBOImpl;
import pe.edu.pucp.inf30.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.EmpleadoDAO;
import pe.edu.pucp.inf30.softprog.dao.almacen.ProductoDAO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.clientes.ClienteDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.almacen.ProductoDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.Genero;
import pe.edu.pucp.inf30.softprog.modelo.almacen.Producto;
import pe.edu.pucp.inf30.softprog.modelo.almacen.UnidadMedida;
import pe.edu.pucp.inf30.softprog.modelo.clientes.CategoriaCliente;
import pe.edu.pucp.inf30.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Cargo;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.inf30.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.inf30.softprog.modelo.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class OrdenVentaBOTest implements GestionableProbable {
    private int testClienteId;
    private int testEmpleadoId;
    private int testProductoId;
    private int testOrdenVentaId;
    private int testCuentaUsuarioId;
    private int testAreaId;
    private final int idIncorrecto = 99999;

    private final OrdenVentaBOImpl ordenVentaBO = new OrdenVentaBOImpl();

    @BeforeAll
    public void inicializar() {
        CuentaUsuarioDAO cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
        CuentaUsuario cuentaUsuario = new CuentaUsuario();
        cuentaUsuario.setUserName("Cuenta de Prueba");
        cuentaUsuario.setPassword("Password de prueba");
        cuentaUsuario.setActivo(true);
        this.testCuentaUsuarioId = cuentaUsuarioDao.crear(cuentaUsuario);
        
        ClienteDAO clienteDao = new ClienteDAOImpl();
        Cliente cliente = new Cliente();
        cliente.setDni("11112222");
        cliente.setNombre("Cliente Test");
        cliente.setApellidoPaterno("Apellido");
        cliente.setCuentaUsuario(cuentaUsuarioDao.leer(this.testCuentaUsuarioId));
        cliente.setGenero(Genero.FEMENINO);
        cliente.setCategoria(CategoriaCliente.ESTANDARD);
        cliente.setFechaNacimiento(new GregorianCalendar(1995, 
                    Calendar.JULY, 10).getTime());
        cliente.setLineaCredito(1000.00);
        cliente.setActivo(true);
        this.testClienteId = clienteDao.crear(cliente);

        AreaDAO areaDao = new AreaDAOImpl();
        Area area = new Area();
        area.setNombre("Area de Prueba");
        area.setActivo(true);
        this.testAreaId = areaDao.crear(area);
        
        EmpleadoDAO empleadoDao = new EmpleadoDAOImpl();
        Empleado empleado = new Empleado();
        empleado.setDni("22223333");
        empleado.setNombre("Empleado Test");
        empleado.setApellidoPaterno("Apellido");
        empleado.setArea(new AreaDAOImpl().leer(this.testAreaId));
        empleado.setGenero(Genero.FEMENINO);
        empleado.setFechaNacimiento(new GregorianCalendar(1995, 
                    Calendar.JULY, 10).getTime());
        
        empleado.setCargo(Cargo.ASISTENTE);
        empleado.setSueldo(1000.00);
        empleado.setActivo(true);
        this.testEmpleadoId = empleadoDao.crear(empleado);

        ProductoDAO productoDao = new ProductoDAOImpl();
        Producto producto = new Producto();
        producto.setNombre("Producto Test");
        producto.setPrecio(3500.00);
        producto.setUnidadMedida(UnidadMedida.Unidad);
        producto.setActivo(true);
        this.testProductoId = productoDao.crear(producto);
    }

    @AfterAll
    public void limpiar() {
//        new ClienteDAOImpl().eliminar(this.testClienteId);
//        new EmpleadoDAOImpl().eliminar(this.testEmpleadoId);
//        new ProductoDAOImpl().eliminar(this.testProductoId);
    }

    @Test
    @Order(1)
    @Override
    public void debeListar() {
        List<OrdenVenta> lista = ordenVentaBO.listar();
        assertNotNull(lista);
    }

    @Test
    @Order(2)
    @Override
    public void debeObtenerSiIdExiste() {
        crearOrdenVenta(); // crea y guarda la orden
        OrdenVenta orden = ordenVentaBO.obtener(this.testOrdenVentaId);
        assertNotNull(orden);
        assertEquals(this.testOrdenVentaId, orden.getId());
    }

    @Test
    @Order(3)
    @Override
    public void noDebeObtenerSiIdNoExiste() {
        OrdenVenta orden = ordenVentaBO.obtener(this.idIncorrecto);
        assertNull(orden);
    }

    @Test
    @Order(4)
    @Override
    public void debeGuardarNuevo() {
        crearOrdenVenta();
        assertTrue(this.testOrdenVentaId > 0);
    }

    @Test
    @Order(5)
    @Override
    public void debeGuardarModificado() {
        OrdenVenta orden = ordenVentaBO.obtener(this.testOrdenVentaId);
        orden.setTotal(200.0);
        ordenVentaBO.guardar(orden, Estado.Modificado);

        OrdenVenta modificada = ordenVentaBO.obtener(this.testOrdenVentaId);
        assertEquals(200.0, modificada.getTotal());
    }

    @Test
    @Order(6)
    @Override
    public void debeEliminarSiIdExiste() {
        ordenVentaBO.eliminar(this.testOrdenVentaId);
        OrdenVenta orden = ordenVentaBO.obtener(this.testOrdenVentaId);
        assertNull(orden);
    }

    @Test
    @Order(7)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        assertThrows(RuntimeException.class, () -> ordenVentaBO.eliminar(idIncorrecto));
    }

    @Test
    @Order(8)
    @Override
    public void debeHacerRollbackSiErrorEnGuardar() {
        OrdenVenta orden = new OrdenVenta();
        orden.setCliente(new ClienteDAOImpl().leer(this.testClienteId));
        orden.setEmpleado(new EmpleadoDAOImpl().leer(this.testEmpleadoId));
        orden.setFecha(new GregorianCalendar(2025, Calendar.JANUARY, 1).getTime());
        orden.setLineas(new ArrayList<>());

        // Forzamos un error: linea sin producto
        LineaOrdenVenta linea = new LineaOrdenVenta();
        linea.setCantidad(1);
        linea.setSubTotal(10.0);
        orden.getLineas().add(linea);

        assertThrows(RuntimeException.class, () -> ordenVentaBO.guardar(orden, Estado.Nuevo));
    }

    @Test
    @Order(9)
    @Override
    public void debeHacerRollbackSiErrorEnEliminar() {
        // Forzamos un error eliminando con id incorrecto
        assertThrows(RuntimeException.class, () -> ordenVentaBO.eliminar(idIncorrecto));
    }

    private void crearOrdenVenta() {
        OrdenVenta orden = new OrdenVenta();
        orden.setCliente(new ClienteDAOImpl().leer(this.testClienteId));
        orden.setEmpleado(new EmpleadoDAOImpl().leer(this.testEmpleadoId));
        orden.setFecha(new GregorianCalendar(2025, Calendar.JANUARY, 1).getTime());

        LineaOrdenVenta linea = new LineaOrdenVenta();
        linea.setProducto(new ProductoDAOImpl().leer(this.testProductoId));
        linea.setCantidad(2);
        linea.setSubTotal(100.0);

        List<LineaOrdenVenta> lineas = new ArrayList<>();
        lineas.add(linea);
        orden.setLineas(lineas);
        orden.setTotal(100.0);

        ordenVentaBO.guardar(orden, Estado.Nuevo);
        this.testOrdenVentaId = orden.getId();
    }
}
