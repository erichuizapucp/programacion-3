package pe.edu.pucp.softprog.bl;

import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.util.List;

public interface EmpleadoBL extends RegistroBL<Empleado, Integer> {
    List<Empleado> filterByName(String nombre) throws BLException;
    Empleado findByCuenta(String userName) throws BLException;
}
