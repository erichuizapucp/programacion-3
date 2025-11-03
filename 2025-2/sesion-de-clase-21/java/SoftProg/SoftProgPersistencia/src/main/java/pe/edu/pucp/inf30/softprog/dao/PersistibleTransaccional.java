package pe.edu.pucp.inf30.softprog.dao;

import java.sql.Connection;

/**
 *
 * @author eric
 */
public interface PersistibleTransaccional<T, I> extends Persistible<T, I> {
    I crear(T modelo, Connection conexion);
    boolean actualizar(T modelo, Connection conexion);
    boolean eliminar(I id, Connection conexion);
}
