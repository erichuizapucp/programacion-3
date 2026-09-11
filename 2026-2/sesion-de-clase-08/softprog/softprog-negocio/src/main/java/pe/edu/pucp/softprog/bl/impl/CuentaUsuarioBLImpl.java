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
