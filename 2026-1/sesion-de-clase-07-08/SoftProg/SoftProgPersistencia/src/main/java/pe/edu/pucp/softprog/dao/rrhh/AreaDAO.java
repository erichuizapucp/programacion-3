package pe.edu.pucp.softprog.dao.rrhh;

import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.util.List;

public interface AreaDAO {
    Integer crear(Area modelo);
    boolean actualizar(Area modelo);
    boolean eliminar(int id);
    Area leer(int id);
    List<Area> leerTodos();
}
