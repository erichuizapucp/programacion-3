package pe.edu.pucp.inf30.softprog.dao.rrhh;

import pe.edu.pucp.inf30.softprog.dao.ICrud;
import pe.edu.pucp.inf30.softprog.model.rrhh.Empleado;

/**
 *
 * @author eric
 */
public interface IEmpleadoDAO extends ICrud<Empleado> {
    Empleado buscarPorDni(String dni);
    // No hay metodos extra por el momento
    // Metodos especificos de Empleado seran agregados aqui
}
