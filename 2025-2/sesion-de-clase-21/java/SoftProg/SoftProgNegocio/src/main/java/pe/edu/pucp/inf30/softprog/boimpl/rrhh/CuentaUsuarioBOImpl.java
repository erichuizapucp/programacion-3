package pe.edu.pucp.inf30.softprog.boimpl.rrhh;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.rrhh.CuentaUsuarioBO;
import pe.edu.pucp.inf30.softprog.dao.rrhh.CuentaUsuarioDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.rrhh.CuentaUsuarioDAOImpl;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.CuentaUsuario;

/**
 *
 * @author eric
 */
public class CuentaUsuarioBOImpl implements CuentaUsuarioBO {
    private final CuentaUsuarioDAO cuentaUsuarioDao;
    
    public CuentaUsuarioBOImpl() {
        this.cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
    }
    
    @Override
    public List<CuentaUsuario> listar() {
        return this.cuentaUsuarioDao.leerTodos();
    }

    @Override
    public CuentaUsuario obtener(int id) {
        return this.cuentaUsuarioDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        this.cuentaUsuarioDao.eliminar(id);
    }

    @Override
    public void guardar(CuentaUsuario modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.cuentaUsuarioDao.crear(modelo);
        }
        else {
            this.cuentaUsuarioDao.actualizar(modelo);
        }
    }

    @Override
    public boolean login(String userName, String password) {
        return this.cuentaUsuarioDao.login(userName, password);
    }
}
