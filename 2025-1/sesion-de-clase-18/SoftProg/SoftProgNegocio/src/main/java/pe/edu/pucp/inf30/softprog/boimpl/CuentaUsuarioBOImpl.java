package pe.edu.pucp.inf30.softprog.boimpl;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.ICuentaUsuarioBO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.ICuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.model.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
public class CuentaUsuarioBOImpl implements ICuentaUsuarioBO {
    private final ICuentaUsuarioDAO cuentaUsuarioDao;
    
    public CuentaUsuarioBOImpl() {
        this.cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
    }
    
    @Override
    public List<CuentaUsuario> listar() {
        return this.cuentaUsuarioDao.listar();
    }

    @Override
    public CuentaUsuario obtener(int id) {
        return this.cuentaUsuarioDao.buscar(id);
    }

    @Override
    public void eliminar(int id) {
        this.cuentaUsuarioDao.eliminar(id);
    }

    @Override
    public void guardar(CuentaUsuario modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.cuentaUsuarioDao.insertar(modelo);
        }
        else {
            this.cuentaUsuarioDao.modificar(modelo);
        }
    }
}