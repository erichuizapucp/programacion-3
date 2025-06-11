package pe.edu.pucp.inf30.softprog.bo.transaccion;

import java.sql.Connection;

/**
 *
 * @author eric
 */
public interface IComandoTransaccional {
    void ejecutar(Connection conexion);
}
