package pe.edu.pucp.inf30.softprog.test.dao.almacen;

import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import pe.edu.pucp.inf30.softprog.dao.almacen.ProductoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.almacen.ProductoDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.almacen.Producto;
import pe.edu.pucp.inf30.softprog.modelo.almacen.UnidadMedida;
import pe.edu.pucp.inf30.softprog.test.dao.PersistibleProbable;

/**
 *
 * @author eric
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductoDaoTest implements PersistibleProbable {
    private int testId;
    private final int idIncorrecto = 99999;
    
    @Test
    @Order(1)
    @Override
    public void debeCrear() {
        Producto producto = new Producto();
        producto.setNombre("Laptop HP");
        producto.setPrecio(3500.00);
        producto.setUnidadMedida(UnidadMedida.Unidad);
        producto.setActivo(true);
        
        ProductoDAO productoDao = new ProductoDAOImpl();
        testId = productoDao.crear(producto);
        producto.setId(testId);
        assertTrue(this.testId > 0);
    }

    @Test
    @Order(2)
    @Override
    public void debeActualizarSiIdExiste() {
        Producto producto = new Producto();
        producto.setId(testId);
        producto.setNombre("Laptop Lenovo");
        producto.setPrecio(4500.00);
        producto.setUnidadMedida(UnidadMedida.Unidad);
        producto.setActivo(false);
        
        ProductoDAO productoDao = new ProductoDAOImpl();
        
        boolean modifico = productoDao.actualizar(producto);
        assertTrue(modifico);

        Producto productoModificado = productoDao.leer(this.testId);
        assertEquals(productoModificado.getNombre(), "Laptop Lenovo");
        assertEquals(productoModificado.getPrecio(), 4500.00);
        assertEquals(productoModificado.getUnidadMedida(), UnidadMedida.Unidad);
        assertFalse(productoModificado.isActivo());
    }

    @Test
    @Order(3)
    @Override
    public void noDebeActualizarSiIdNoExiste() {
        Producto producto = new Producto();
        producto.setId(idIncorrecto);
        producto.setNombre("Laptop Lenovo");
        producto.setPrecio(4500.00);
        producto.setUnidadMedida(UnidadMedida.Unidad);
        producto.setActivo(true);
        
        ProductoDAO productoDao = new ProductoDAOImpl();
        boolean modifico = productoDao.actualizar(producto);
        
        assertFalse(modifico);
    }

    @Test
    @Order(4)
    @Override
    public void noDebeEliminarSiIdNoExiste() {
        ProductoDAO productoDao = new ProductoDAOImpl();
        boolean elimino = productoDao.eliminar(this.idIncorrecto);
        assertFalse(elimino);
    }

    @Test
    @Order(5)
    @Override
    public void debeLeerSiIdExiste() {
        ProductoDAO productoDao = new ProductoDAOImpl();
        Producto producto = productoDao.leer(this.testId);
        assertNotNull(producto);
    }

    @Test
    @Order(6)
    @Override
    public void noDebeLeerSiIdNoExiste() {
        ProductoDAO productoDao = new ProductoDAOImpl();
        Producto producto = productoDao.leer(this.idIncorrecto);
        assertNull(producto);
    }

    @Test
    @Order(7)
    @Override
    public void debeLeerTodos() {
        ProductoDAO productoDao = new ProductoDAOImpl();
        List<Producto> productos = productoDao.leerTodos();
        
        assertNotNull(productos);
        assertFalse(productos.isEmpty());
    }

    @Test
    @Order(8)
    @Override
    public void debeEliminarSiIdExiste() {
        ProductoDAO productoDao = new ProductoDAOImpl();
        boolean elimino = productoDao.eliminar(this.testId);
        assertTrue(elimino);
    }
}
