package pe.edu.pucp.inf30.softprog.pruebas;

import java.util.List;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;
import pe.edu.pucp.inf30.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;

/**
 *
 * @author eric
 */
public class SoftProgPruebas {

    public static void main(String[] args) {
        Area area = new Area();
        area.setNombre("Recursos Humanos");
        area.setActivo(true);
        
        AreaDAO areaDao = new AreaDAOImpl();
        int id = areaDao.crear(area);
        area.setId(id);
        
        area.setNombre("Ventas");
        areaDao.actualizar(area);
        
        List<Area> areas = areaDao.leerTodos();
        for (Area a : areas) {
            System.out.println("Id: " + a.getId());
            System.out.println("Nombre: " + a.getNombre());
            System.out.println("Activo: " + a.isActivo());
        }
        
        areaDao.eliminar(area.getId());
    }
}
