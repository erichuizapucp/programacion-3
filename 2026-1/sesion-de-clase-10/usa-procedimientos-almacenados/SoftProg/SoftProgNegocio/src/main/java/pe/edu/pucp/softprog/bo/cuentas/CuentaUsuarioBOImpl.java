package pe.edu.pucp.softprog.bo.cuentas;

import pe.edu.pucp.softprog.bo.BaseBO;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAO;
import pe.edu.pucp.softprog.dao.cuentas.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.rrhh.CuentaUsuario;

import java.util.List;
import java.util.Objects;

public class CuentaUsuarioBOImpl extends BaseBO implements CuentaUsuarioBO {
    private final CuentaUsuarioDAO cuentaUsuarioDao;

    public CuentaUsuarioBOImpl() {
        this.cuentaUsuarioDao = new CuentaUsuarioDAOImpl();
    }

    @Override
    public boolean login(String username, String password) {
        validarTextoObligatorio(username, "username");
        validarTextoObligatorio(password, "password");
        return this.cuentaUsuarioDao.login(username, password);
    }

    @Override
    public List<CuentaUsuario> listar() {
        return this.cuentaUsuarioDao.leerTodos();
    }

    @Override
    public CuentaUsuario obtener(int id) {
        validarIdPositivo(id, "id");
        return this.cuentaUsuarioDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        validarIdPositivo(id, "id");
        if (!this.cuentaUsuarioDao.eliminar(id)) {
            throw new IllegalStateException("No se pudo eliminar la cuenta de usuario con id: " + id);
        }
    }

    @Override
    public void guardar(CuentaUsuario modelo, Estado estado) {
        validarCuentaUsuario(modelo);
        validarEstado(estado);

        if (estado == Estado.Nuevo) {
            int id = this.cuentaUsuarioDao.crear(modelo);
            if (id <= 0) {
                throw new IllegalStateException("No se pudo crear la cuenta de usuario");
            }
            modelo.setId(id);
        }
        else if (estado == Estado.Modificado) {
            validarIdPositivo(modelo.getId(), "id de la cuenta de usuario");
            if (!this.cuentaUsuarioDao.actualizar(modelo)) {
                throw new IllegalStateException("No se pudo actualizar la cuenta de usuario con id: " + modelo.getId());
            }
        }
        else {
            throw new IllegalArgumentException("Estado no soportado en guardar: " + estado);
        }
    }

    private void validarCuentaUsuario(CuentaUsuario modelo) {
        Objects.requireNonNull(modelo, "La cuenta de usuario es obligatoria");
        validarTextoObligatorio(modelo.getUserName(), "userName");
        validarTextoObligatorio(modelo.getPassword(), "password");
    }
}

