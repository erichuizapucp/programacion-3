package pe.edu.pucp.inf30.softprog.dao.ventas;

import java.sql.Connection;
import java.util.List;
import pe.edu.pucp.inf30.softprog.dao.PersistibleTransaccional;
import pe.edu.pucp.inf30.softprog.modelo.ventas.LineaOrdenVenta;

/**
 *
 * @author eric
 */
public interface LineaOrdenVentaDAO 
        extends PersistibleTransaccional<LineaOrdenVenta, Integer> {
    
    List<LineaOrdenVenta> leerTodosPorOrden(int idOrden);
    List<LineaOrdenVenta> leerTodosPorOrden(int idOrden, Connection conn);
}
