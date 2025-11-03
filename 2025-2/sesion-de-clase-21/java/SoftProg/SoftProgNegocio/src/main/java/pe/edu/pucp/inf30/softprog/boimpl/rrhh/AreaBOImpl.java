package pe.edu.pucp.inf30.softprog.boimpl.rrhh;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.rrhh.AreaBO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.AreaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Area;

/**
 *
 * @author eric
 */
public class AreaBOImpl implements AreaBO {
    private final AreaDAO areaDao;
    
    public AreaBOImpl() {
        this.areaDao = new AreaDAOImpl();
    }
    
    @Override
    public List<Area> listar() {
        return this.areaDao.leerTodos();
    }

    @Override
    public Area obtener(int id) {
        return this.areaDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        this.areaDao.eliminar(id);
    }

    @Override
    public void guardar(Area modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            int id = this.areaDao.crear(modelo);
            modelo.setId(id);
        }
        else {
            this.areaDao.actualizar(modelo);
        }
    }   
}
