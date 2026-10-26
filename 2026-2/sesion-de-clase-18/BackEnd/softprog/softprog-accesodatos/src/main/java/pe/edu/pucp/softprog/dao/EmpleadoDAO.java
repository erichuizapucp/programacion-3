package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.SQLException;
import java.util.List;


public interface EmpleadoDAO extends DAO<Empleado, Integer> {
    Empleado findByDni(String dni) throws SQLException;
    List<Empleado> filterByName(String nombre) throws SQLException;
    Empleado findByCuenta(String userName) throws SQLException;
}
