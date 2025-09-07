package pe.edu.pucp.inf30.softprog.pruebas;

import java.sql.SQLException;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;
import pe.edu.pucp.inf30.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;

/**
 *
 * @author eric
 */
public class SoftProgPruebas {

    public static void main(
            String[] args) throws SQLException, ClassNotFoundException {
        
        Area modelo = new Area();
        modelo.setNombre("Recursos Humanos");
        modelo.setActivo(true);
        
        AreaDAO areaDao = new AreaDAOImpl();
        int id = areaDao.crear(modelo);
        modelo.setId(id);
        
        System.out.println(modelo);
    }
}
