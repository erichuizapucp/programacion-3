package pe.edu.pucp.inf30.softprogapptest;

import java.util.List;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IAreaDAO;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;
import pe.edu.pucp.inf30.softprog.rrhh.mysql.AreaDAOImpl;

public class SoftProgAppTest {

    public static void main(String[] args) {
        IAreaDAO areaDao = new AreaDAOImpl();
        List<Area> areas = areaDao.listar();
        
        for (Area area : areas) {
            System.out.println("Codigo: " + area.getId());
            System.out.println("Nombre: " + area.getNombre());
            System.out.println("Activo: " + area.isActivo());
        }
    }
}
