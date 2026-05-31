package pe.edu.pucp.softprog.dao.ventas;

import pe.edu.pucp.softprog.dao.Persistible;
import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.util.List;

public interface OrdenVentaDAO extends Persistible<OrdenVenta, Integer> {
    List<OrdenVenta> listarOrdenesVentaPorCuenta(String cuenta);
}
