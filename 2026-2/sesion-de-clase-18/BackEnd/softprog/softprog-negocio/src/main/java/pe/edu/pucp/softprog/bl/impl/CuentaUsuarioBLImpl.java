package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.dao.CuentaUsuarioDAO;
import pe.edu.pucp.softprog.dao.impl.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class CuentaUsuarioBLImpl implements CuentaUsuarioBL {
    private static final Pattern CORREO =
            Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");
    private static final int PASSWORD_MIN = 6;

    private final CuentaUsuarioDAO cuentaUsuarioDAO = new CuentaUsuarioDAOImpl();

    @Override
    public List<CuentaUsuario> findAll() throws BLException {
        try {
            return cuentaUsuarioDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar las cuentas de usuario", e);
        }
    }

    @Override
    public CuentaUsuario findById(Integer id) throws BLException {
        try {
            return cuentaUsuarioDAO.findById(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar la cuenta de usuario", e);
        }
    }

    @Override
    public CuentaUsuario findByUserName(String userName) throws BLException {
        try {
            return cuentaUsuarioDAO.findByUserName(userName);
        } catch (SQLException e) {
            throw new BLException("No se pudo obtener la cuenta de usuario", e);
        }
    }

    @Override
    public boolean autenticar(String userName, String password) throws BLException {
        CuentaUsuario cuenta = findByUserName(userName);
        return cuenta != null && cuenta.isActivo() && cuenta.getPassword().equals(password);
    }

    @Override
    public void cambiarPassword(int id, String passwordActual, String passwordNueva) throws BLException {
        CuentaUsuario cuenta;
        try {
            cuenta = cuentaUsuarioDAO.findById(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la cuenta de usuario", e);
        }

        if (cuenta == null || !cuenta.getPassword().equals(passwordActual)) {
            throw new BLException("La contraseña actual no es correcta");
        }
        if (passwordNueva == null || passwordNueva.length() < PASSWORD_MIN) {
            throw new BLException(
                    "La contraseña debe tener al menos " + PASSWORD_MIN + " caracteres");
        }
        if (passwordNueva.equals(passwordActual)) {
            throw new BLException("La nueva contraseña debe ser diferente de la actual");
        }

        try {
            cuentaUsuarioDAO.updatePassword(id, passwordNueva);
        } catch (SQLException e) {
            throw new BLException("No se pudo cambiar la contraseña", e);
        }
    }

    @Override
    public void insert(CuentaUsuario cuentaUsuario) throws BLException {
        validarFormato(cuentaUsuario);
        validarUserNameUnico(cuentaUsuario);
        try {
            cuentaUsuarioDAO.insert(cuentaUsuario);
        } catch (SQLException e) {
            throw new BLException("No se pudo registrar la cuenta de usuario", e);
        }
    }

    @Override
    public void update(CuentaUsuario cuentaUsuario) throws BLException {
        validarExiste(cuentaUsuario.getId());
        validarFormato(cuentaUsuario);
        validarUserNameUnico(cuentaUsuario);
        try {
            cuentaUsuarioDAO.update(cuentaUsuario);
        } catch (SQLException e) {
            throw new BLException("No se pudo actualizar la cuenta de usuario", e);
        }
    }

    @Override
    public void delete(Integer id) throws BLException {
        try {
            cuentaUsuarioDAO.delete(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo eliminar la cuenta de usuario", e);
        }
    }

    private void validarFormato(CuentaUsuario cuenta) throws BLException {
        if (!CORREO.matcher(cuenta.getUserName()).matches()) {
            throw new BLException("El usuario debe tener formato de correo electrónico");
        }
        if (cuenta.getPassword().length() < PASSWORD_MIN) {
            throw new BLException(
                    "La contraseña debe tener al menos " + PASSWORD_MIN + " caracteres");
        }
    }

    private void validarExiste(int id) throws BLException {
        try {
            if (cuentaUsuarioDAO.findById(id) == null) {
                throw new BLException("No existe una cuenta de usuario con id " + id);
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la existencia de la cuenta", e);
        }
    }

    private void validarUserNameUnico(CuentaUsuario cuenta) throws BLException {
        try {
            CuentaUsuario existente = cuentaUsuarioDAO.findByUserName(cuenta.getUserName());
            if (existente != null && existente.getId() != cuenta.getId()) {
                throw new BLException(
                        "Ya existe una cuenta con el usuario '" + cuenta.getUserName() + "'");
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la unicidad del usuario", e);
        }
    }
}
