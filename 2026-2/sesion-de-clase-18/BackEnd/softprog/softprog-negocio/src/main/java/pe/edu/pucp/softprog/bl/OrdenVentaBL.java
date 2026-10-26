package pe.edu.pucp.softprog.bl;

import pe.edu.pucp.softprog.modelo.ventas.OrdenVenta;

import java.util.List;

public interface OrdenVentaBL extends RegistroBL<OrdenVenta, Integer> {
    List<OrdenVenta> findByCuenta(String userName) throws BLException;
}
