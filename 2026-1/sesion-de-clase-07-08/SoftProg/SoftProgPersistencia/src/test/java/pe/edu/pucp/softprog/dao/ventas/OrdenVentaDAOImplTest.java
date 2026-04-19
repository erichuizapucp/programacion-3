package pe.edu.pucp.softprog.dao.ventas;

import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.MockedStatic;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.softprog.modelo.ventas.LineaOrdenVenta;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OrdenVentaDAOImplTest {

	@Test
	void deberiaCrearMaestroYDetalleCuandoOrdenTieneLineas() throws Exception {
		DBManager manager = mock(DBManager.class);
		Connection conn = mock(Connection.class);
		PreparedStatement cmdOrden = mock(PreparedStatement.class);
		PreparedStatement cmdLinea = mock(PreparedStatement.class);
		ResultSet keys = mock(ResultSet.class);

		when(manager.getConnection()).thenReturn(conn);
		when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(cmdOrden, cmdLinea);
		when(cmdOrden.executeUpdate()).thenReturn(1);
		when(cmdOrden.getGeneratedKeys()).thenReturn(keys);
		when(keys.next()).thenReturn(true);
		when(keys.getInt(1)).thenReturn(50);
		when(cmdLinea.executeUpdate()).thenReturn(1);

		Producto producto = new Producto();
		producto.setId(9);
		LineaOrdenVenta linea = new LineaOrdenVenta();
		linea.setProducto(producto);
		linea.setCantidad(2);
		linea.setSubTotal(20);
		linea.setActivo(true);

		Cliente cliente = new Cliente();
		cliente.setId(3);
		Empleado empleado = new Empleado();
		empleado.setId(4);

		OrdenVenta orden = new OrdenVenta();
		orden.setCliente(cliente);
		orden.setEmpleado(empleado);
		orden.setTotal(20);
		orden.setActivo(true);
		orden.setLineas(List.of(linea));

		try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
			mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
			Integer id = new OrdenVentaDAOImpl().crear(orden);
			assertEquals(50, id);
		}
	}

	@Test
	void deberiaEliminarDetalleAntesDeMaestroCuandoEliminaOrden() throws Exception {
		DBManager manager = mock(DBManager.class);
		Connection conn = mock(Connection.class);
		PreparedStatement cmdLineas = mock(PreparedStatement.class);
		PreparedStatement cmdOrden = mock(PreparedStatement.class);

		when(manager.getConnection()).thenReturn(conn);
		when(conn.prepareStatement(anyString())).thenReturn(cmdLineas, cmdOrden);
		when(cmdLineas.executeUpdate()).thenReturn(1);
		when(cmdOrden.executeUpdate()).thenReturn(1);

		try (MockedStatic<DBFactoryProvider> mocked = mockStatic(DBFactoryProvider.class)) {
			mocked.when(DBFactoryProvider::getManager).thenReturn(manager);
			boolean eliminado = new OrdenVentaDAOImpl().eliminar(10);
			assertTrue(eliminado);
		}

		InOrder inOrder = inOrder(cmdLineas, cmdOrden);
		inOrder.verify(cmdLineas).executeUpdate();
		inOrder.verify(cmdOrden).executeUpdate();
	}
}

