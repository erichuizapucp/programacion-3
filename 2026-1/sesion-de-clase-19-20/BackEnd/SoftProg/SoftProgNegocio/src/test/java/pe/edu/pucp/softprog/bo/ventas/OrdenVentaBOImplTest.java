package pe.edu.pucp.softprog.bo.ventas;

import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.dao.TransactionsManager;
import pe.edu.pucp.softprog.dao.ventas.OrdenVentaDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrdenVentaBOImplTest {

    @Test
    void guardarNuevoDebeConfirmarTransaccion() {
        try (MockedConstruction<OrdenVentaDAOImpl> mockedDao = mockConstruction(OrdenVentaDAOImpl.class,
                (dao, context) -> when(dao.crear(any(OrdenVenta.class))).thenReturn(21));
             MockedStatic<TransactionsManager> tx = mockStatic(TransactionsManager.class)) {
            OrdenVentaBOImpl bo = new OrdenVentaBOImpl();
            OrdenVenta orden = crearOrdenValida();

            bo.guardar(orden, Estado.Nuevo);

            OrdenVentaDAOImpl daoMock = mockedDao.constructed().getFirst();
            tx.verify(TransactionsManager::iniciarTransaccion);
            tx.verify(TransactionsManager::commitTransaccion);
            tx.verify(TransactionsManager::rollbackTransaccion, never());

            assertEquals(21, orden.getId());
            verify(daoMock).crear(orden);
        }
    }

    @Test
    void guardarNuevoDebeRollbackSiDaoFalla() {
        try (MockedConstruction<OrdenVentaDAOImpl> mockedDao = mockConstruction(OrdenVentaDAOImpl.class,
                (dao, context) -> when(dao.crear(any(OrdenVenta.class))).thenThrow(new RuntimeException("error")));
             MockedStatic<TransactionsManager> tx = mockStatic(TransactionsManager.class)) {
            OrdenVentaBOImpl bo = new OrdenVentaBOImpl();

            assertThrows(RuntimeException.class, () -> bo.guardar(crearOrdenValida(), Estado.Nuevo));
            tx.verify(TransactionsManager::iniciarTransaccion);
            tx.verify(TransactionsManager::rollbackTransaccion);
            tx.verify(TransactionsManager::commitTransaccion, never());

            OrdenVentaDAOImpl daoMock = mockedDao.constructed().getFirst();
            verify(daoMock).crear(any(OrdenVenta.class));
        }
    }

    private OrdenVenta crearOrdenValida() {
        Producto producto = new Producto();
        producto.setId(3);

        LineaOrdenVenta linea = new LineaOrdenVenta();
        linea.setProducto(producto);
        linea.setCantidad(2);
        linea.setSubTotal(50);
        linea.setActivo(true);

        Cliente cliente = new Cliente();
        cliente.setId(8);

        OrdenVenta orden = new OrdenVenta();
        orden.setCliente(cliente);
        orden.setLineas(List.of(linea));
        orden.setTotal(50);
        orden.setActivo(true);
        return orden;
    }
}

