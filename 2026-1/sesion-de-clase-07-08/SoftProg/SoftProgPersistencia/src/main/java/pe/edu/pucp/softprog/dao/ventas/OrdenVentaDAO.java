package pe.edu.pucp.softprog.dao.ventas;

import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.util.List;

public interface OrdenVentaDAO {
    Integer crear(OrdenVenta modelo);
    boolean actualizar(OrdenVenta modelo);
    boolean eliminar(int id);
    OrdenVenta leer(int id);
    List<OrdenVenta> leerTodos();
}
