package pe.edu.pucp.inf30.softprog.bo;

import pe.edu.pucp.inf30.softprog.model.rrhh.Empleado;

/**
 *
 * @author eric
 */
public interface IEmpleadoBO extends IBaseBO<Empleado> {
    Empleado buscarPorDni(String dni);
}
