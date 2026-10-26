package pe.edu.pucp.softprog.bl;

import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.util.List;

public interface AreaBL extends RegistroBL<Area, Integer> {
    List<Area> filterByName(String nombre) throws BLException;
}
