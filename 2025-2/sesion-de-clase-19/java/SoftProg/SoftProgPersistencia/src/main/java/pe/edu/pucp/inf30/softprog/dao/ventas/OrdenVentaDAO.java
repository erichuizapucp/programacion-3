package pe.edu.pucp.inf30.softprog.dao.ventas;

import java.util.List;
import pe.edu.pucp.inf30.softprog.dao.PersistibleTransaccional;
import pe.edu.pucp.inf30.softprog.modelo.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
public interface OrdenVentaDAO extends PersistibleTransaccional<OrdenVenta, Integer> {
    List<OrdenVenta> listarOrdenesVentaPorCuenta(String cuenta);
}
