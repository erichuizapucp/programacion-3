package pe.edu.pucp.inf30.softprog.bo.transaccion;

import java.sql.Connection;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.model.logistica.ventas.OrdenVenta;

/**
 *
 * @author eric
 */
public interface IComandoTransaccional {
    void ejecutar(Connection conexion);
}
