package pe.edu.pucp.softprog.bo.ventas;

import pe.edu.pucp.softprog.bo.Gestionable;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.util.List;

public interface OrdenVentaBO extends Gestionable<OrdenVenta> {
    List<OrdenVenta> listarOrdenesVentaPorCuenta(String cuenta);
}
