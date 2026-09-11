package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.SQLException;


public interface EmpleadoDAO extends DAO<Empleado, Integer> {
    Empleado findByDni(String dni) throws SQLException;
}
