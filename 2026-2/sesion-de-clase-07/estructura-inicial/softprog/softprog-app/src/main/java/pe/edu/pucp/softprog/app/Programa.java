package pe.edu.pucp.softprog.app;

import pe.edu.pucp.softprog.dao.AreaDAO;
import pe.edu.pucp.softprog.dao.impl.AreaDAOImpl;
import pe.edu.pucp.softprog.db.DBManager;
import pe.edu.pucp.softprog.modelo.rrhh.Area;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Programa {

    public static void main(String[] args) throws SQLException {
        AreaDAO areaDAO = new AreaDAOImpl();
//        areaDAO.delete(7);

        Area nuevaArea = new Area();
        nuevaArea.setNombre("Nueva Area 3");
        nuevaArea.setActivo(true);
        areaDAO.insert(nuevaArea);

        nuevaArea.setActivo(false);
        areaDAO.update(nuevaArea);

        List<Area> areas = areaDAO.findAll();

        for (Area area : areas) {
            System.out.println("ID: " + area.getId());
            System.out.println("Nombre: " + area.getNombre());
            System.out.println("Activo: " + area.isActivo());
            System.out.println("-------------------------");
        }

//        Area area = areaDAO.findById(2);
//        if (area != null) {
//            System.out.println("ID: " + area.getId());
//            System.out.println("Nombre: " + area.getNombre());
//            System.out.println("Activo: " + area.isActivo());
//        }
    }
}
