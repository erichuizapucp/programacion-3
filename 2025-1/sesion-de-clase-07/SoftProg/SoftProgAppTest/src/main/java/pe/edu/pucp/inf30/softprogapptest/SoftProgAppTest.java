package pe.edu.pucp.inf30.softprogapptest;

import java.util.List;
import pe.edu.pucp.inf30.log.LogManager;
import pe.edu.pucp.inf30.softprog.activerecord.AreaActiveRecord;
//import pe.edu.pucp.inf30.softprog.activerecord.AreaActiveRecord;
import pe.edu.pucp.inf30.softprog.rrhh.model.Area;
import pe.edu.pucp.inf30.softprog.rrhh.dao.IAreaDAO;
import pe.edu.pucp.inf30.softprog.rrhh.mysql.AreaDAOImpl;
//import pe.edu.pucp.inf30.softprog.rrhh.dao.IAreaDAO;
//import pe.edu.pucp.inf30.softprog.rrhh.model.AreaActiveRecord;
//import pe.edu.pucp.inf30.softprog.rrhh.mysql.AreaDAOImpl;
public class SoftProgAppTest {

    public static void main(String[] args) {
        /*AreaActiveRecord area = new AreaActiveRecord();
        area.setNombre("RRHH");
        area.setActivo(true);
        int id = area.insertar();
        area.setId(id);
        
        area.setActivo(false);
        if (area.actualizar()) {
            System.out.println("El registro fue actualizado correctamente");
        }
        
        area = AreaActiveRecord.buscar(id);
        System.out.println("Codigo: " + area.getId());
        System.out.println("Nombre: " + area.getNombre());
        System.out.println("Activo: " + area.isActivo());
        
        if (area.eliminar()) {
            System.out.println("El registro fue eliminado");
        }*/
        
        Area rrhh = new Area();
        rrhh.setNombre("RRHH");
        rrhh.setActivo(true);
        
        IAreaDAO areaDao = new AreaDAOImpl();
        int id = areaDao.insertar(rrhh);
        LogManager.logInfo("Se creo el registro con id: " + id);
        rrhh.setId(id);
        rrhh.setActivo(false);
        
        if (areaDao.modificar(rrhh)) {
            LogManager.logInfo("El registro fue actualizado correctamente.");
        }
        
        List<Area> areas = areaDao.listar();
        
        LogManager.logInfo("Lista todas las areas");
        for (Area area : areas) {
            System.out.println("Codigo: " + area.getId());
            System.out.println("Nombre: " + area.getNombre());
            System.out.println("Activo: " + area.isActivo());
        }
        
        Area rrhh2 = areaDao.buscar(rrhh.getId());
        if (rrhh2 != null) {
            LogManager.logInfo("Se encontro el area con id: " + rrhh2.getId());
            
            System.out.println("Codigo: " + rrhh2.getId());
            System.out.println("Nombre: " + rrhh2.getNombre());
            System.out.println("Activo: " + rrhh2.isActivo());
        }
        
        if (areaDao.eliminar(rrhh2.getId())) {
            LogManager.logInfo("El registro fue eliminado correctamente.");
        }
    }
}
