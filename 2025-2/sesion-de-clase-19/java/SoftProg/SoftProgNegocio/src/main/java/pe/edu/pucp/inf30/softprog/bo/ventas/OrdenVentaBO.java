package pe.edu.pucp.inf30.softprog.bo.ventas;

import java.util.List;
import pe.edu.pucp.inf30.softprog.modelo.ventas.OrdenVenta;
import pe.edu.pucp.inf30.softprog.bo.Gestionable;

/**
 *
 * @author eric
 */
public interface OrdenVentaBO extends Gestionable<OrdenVenta> {
    List<OrdenVenta> listarOrdenesVentaPorCuenta(String cuenta);
}
