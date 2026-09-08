package pe.edu.pucp.softprog.dao;

import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.SQLException;
import java.util.List;


public interface EmpleadoDAO {
    List<Empleado> findAll() throws SQLException;
    Empleado findById(Integer id) throws SQLException;
    void insert(Empleado empleado) throws SQLException;
    void update(Empleado empleado) throws SQLException;
    void delete(Integer id) throws SQLException;
}
