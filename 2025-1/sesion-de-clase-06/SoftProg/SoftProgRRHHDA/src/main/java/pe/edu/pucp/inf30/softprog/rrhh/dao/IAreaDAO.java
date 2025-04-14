package pe.edu.pucp.inf30.softprog.rrhh.dao;

import java.util.List;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;

public interface IAreaDAO {
    int insertar(Area area);
    int modificar(Area area);
    boolean eliminar(int id);
    Area obtenerPorId(int id);
    List<Area> listar();
}
