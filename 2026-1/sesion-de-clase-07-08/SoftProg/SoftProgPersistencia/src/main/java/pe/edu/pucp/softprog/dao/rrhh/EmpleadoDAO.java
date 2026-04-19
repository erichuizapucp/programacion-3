package pe.edu.pucp.softprog.dao.rrhh;

import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.util.List;

public interface EmpleadoDAO {
    Integer crear(Empleado modelo);
    boolean actualizar(Empleado modelo);
    boolean eliminar(int id);
    Empleado leer(int id);
    List<Empleado> leerTodos();

    Empleado buscarPorDni(String dni);
}
