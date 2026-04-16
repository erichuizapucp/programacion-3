package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.softprog.daoimpl.rrhh.AreaDAOImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.util.List;

public class Program {
    public static void main(String[] args) {
//        Area area = new Area();
//        area.setNombre("Recursos Humanos");
//        area.setActivo(true);
//
//        AreaDAO areaDAO = new AreaDAOImpl();
//        int id = areaDAO.crear(area);
//
//        area.setId(id);
//        area.setActivo(false);
//
//        areaDAO.actualizar(area);

//        AreaDAO areaDAO = new AreaDAOImpl();
//        areaDAO.eliminar(1);

//        AreaDAO areaDAO = new AreaDAOImpl();
//        List<Area> areas = areaDAO.leerTodos();
//
//        for (Area area : areas) {
//            System.out.println(area);
//        }

        AreaDAO areaDAO = new AreaDAOImpl();
        Area area = areaDAO.leer(1);
        System.out.println(area);
    }
}
