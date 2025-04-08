package pe.edu.pucp.inf30.softprogrrhhda;

import pe.edu.pucp.inf30.softprogrrhhmodel.Area;

public interface IAreaDAO {
    int insertarArea(Area area);
    int actualizarArea(Area area);
    boolean eliminarArea(int id);
    Area obtenerArea(int id);
}
