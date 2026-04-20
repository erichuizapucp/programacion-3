package pe.edu.pucp.inf30.softprog.daoimpl;

import java.sql.Connection;
import pe.edu.pucp.inf30.softprog.dao.PersistibleTransaccional;

/**
 *
 * @author eric
 */
public abstract class TransaccionalBaseDAO<T> extends BaseDAO<T> 
        implements PersistibleTransaccional<T, Integer> {
    
    @Override
    public Integer crear(T modelo, Connection conexion) {
        return ejecutarComandoCrear(conexion, modelo);
    }

    @Override
    public boolean actualizar(T modelo, Connection conexion) {
        return ejecutarComandoActualizar(conexion, modelo);
    }

    @Override
    public boolean eliminar(Integer id, Connection conexion) {
        return ejecutarComandoEliminar(conexion, id);
    }
}
