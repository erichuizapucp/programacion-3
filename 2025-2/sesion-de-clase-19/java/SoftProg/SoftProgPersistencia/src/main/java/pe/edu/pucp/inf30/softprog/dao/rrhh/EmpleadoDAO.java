package pe.edu.pucp.inf30.softprog.dao.rrhh;

import pe.edu.pucp.inf30.softprog.dao.Persistible;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;

/**
 *
 * @author eric
 */
public interface EmpleadoDAO extends Persistible<Empleado, Integer> {
    Empleado buscarPorDni(String dni);
}
