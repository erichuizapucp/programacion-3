package pe.edu.pucp.inf30.softprog.boimpl;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.IEmpleadoBO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.IEmpleadoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.rrhh.Empleado;

/**
 *
 * @author eric
 */
public class EmpleadoBOImpl implements IEmpleadoBO {
    private final IEmpleadoDAO empleadoDao;
    
    public EmpleadoBOImpl() {
        this.empleadoDao = new EmpleadoDAOImpl();
    }
    
    @Override
    public List<Empleado> listar() {
        return this.empleadoDao.listar();
    }

    @Override
    public Empleado obtener(int id) {
        return this.empleadoDao.buscar(id);
    }

    @Override
    public void eliminar(int id) {
        this.empleadoDao.eliminar(id);
    }

    @Override
    public void guardar(Empleado modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.empleadoDao.insertar(modelo);
        }
        else {
            this.empleadoDao.modificar(modelo);
        }
    }

    @Override
    public Empleado buscarPorDni(String dni) {
        return this.empleadoDao.buscarPorDni(dni);
    }
}
