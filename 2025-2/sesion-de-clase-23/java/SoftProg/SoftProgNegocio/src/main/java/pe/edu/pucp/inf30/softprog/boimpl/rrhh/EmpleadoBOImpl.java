package pe.edu.pucp.inf30.softprog.boimpl.rrhh;

import java.util.List;
import pe.edu.pucp.inf30.softprog.dao.rrhh.EmpleadoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.EmpleadoDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;
import pe.edu.pucp.inf30.softprog.bo.rrhh.EmpleadoBO;

/**
 *
 * @author eric
 */
public class EmpleadoBOImpl implements EmpleadoBO {

    private final EmpleadoDAO empleadoDao;
    
    public EmpleadoBOImpl() {
        this.empleadoDao = new EmpleadoDAOImpl();
    }
    
    @Override
    public List<Empleado> listar() {
        return this.empleadoDao.leerTodos();
    }

    @Override
    public Empleado obtener(int id) {
        return this.empleadoDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        this.empleadoDao.eliminar(id);
    }

    @Override
    public void guardar(Empleado modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.empleadoDao.crear(modelo);
        }
        else {
            this.empleadoDao.actualizar(modelo);
        }
    }

    @Override
    public Empleado buscarPorDni(String dni) {
        return this.empleadoDao.buscarPorDni(dni);
    }
}
