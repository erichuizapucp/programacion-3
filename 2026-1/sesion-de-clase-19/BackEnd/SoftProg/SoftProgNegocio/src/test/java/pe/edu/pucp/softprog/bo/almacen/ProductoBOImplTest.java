package pe.edu.pucp.softprog.bo.almacen;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import pe.edu.pucp.softprog.dao.almacen.ProductoDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.almacen.UnidadMedida;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

class ProductoBOImplTest {

    @Test
    void guardarDebeValidarNombreObligatorio() {
        try (MockedConstruction<ProductoDAOImpl> mocked = mockConstruction(ProductoDAOImpl.class)) {
            ProductoBOImpl bo = new ProductoBOImpl();
            Producto producto = crearProductoValido();
            producto.setNombre(" ");

            assertThrows(IllegalArgumentException.class, () -> bo.guardar(producto, Estado.Nuevo));
            ProductoDAOImpl daoMock = mocked.constructed().getFirst();
            verifyNoInteractions(daoMock);
        }
    }

    @Test
    void guardarNuevoDebeFallarCuandoDaoNoCrea() {
        try (MockedConstruction<ProductoDAOImpl> mocked = mockConstruction(ProductoDAOImpl.class,
                (dao, ignored) -> when(dao.crear(any())).thenReturn(0))) {
            ProductoBOImpl bo = new ProductoBOImpl();
            Producto producto = crearProductoValido();

            assertThrows(IllegalStateException.class, () -> bo.guardar(producto, Estado.Nuevo));
            ProductoDAOImpl daoMock = mocked.constructed().getFirst();
            verify(daoMock).crear(producto);
        }
    }

    private Producto crearProductoValido() {
        Producto producto = new Producto();
        producto.setNombre("Laptop");
        producto.setUnidadMedida(UnidadMedida.UND);
        producto.setPrecio(1500);
        producto.setActivo(true);
        return producto;
    }
}


