package pe.edu.pucp.inf30.softprog.boimpl;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.IAreaBO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.IAreaDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.AreaDAOImpl;
import pe.edu.pucp.inf30.softprog.model.rrhh.Area;

/**
 *
 * @author eric
 */
public class AreaBOImpl implements IAreaBO {
    private final IAreaDAO areaDao;
    
    public AreaBOImpl() {
        this.areaDao = new AreaDAOImpl();
    }
    
    @Override
    public List<Area> listar() {
        return this.areaDao.listar();
    }

    @Override
    public Area obtener(int id) {
        return this.areaDao.buscar(id);
    }

    @Override
    public void eliminar(int id) {
        this.areaDao.eliminar(id);
    }

    @Override
    public void guardar(Area modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.areaDao.insertar(modelo);
        }
        else {
            this.areaDao.modificar(modelo);
        }
    }   
}