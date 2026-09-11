package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.EmpleadoBL;
import pe.edu.pucp.softprog.dao.EmpleadoDAO;
import pe.edu.pucp.softprog.dao.impl.EmpleadoDAOImpl;
import pe.edu.pucp.softprog.modelo.rrhh.Empleado;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class EmpleadoBLImpl implements EmpleadoBL {
    private static final double SUELDO_MINIMO = 1130.0;
    private static final int EDAD_MINIMA = 18;

    private final EmpleadoDAO empleadoDAO = new EmpleadoDAOImpl();

    @Override
    public List<Empleado> findAll() throws BLException {
        try {
            return empleadoDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar los empleados", e);
        }
    }

    @Override
    public Empleado findById(Integer id) throws BLException {
        try {
            return empleadoDAO.findById(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el empleado", e);
        }
    }

    @Override
    public void insert(Empleado empleado) throws BLException {
        validarDatos(empleado);
        validarDniUnico(empleado);
        try {
            empleadoDAO.insert(empleado);
        } catch (SQLException e) {
            throw new BLException("No se pudo registrar el empleado", e);
        }
    }

    @Override
    public void update(Empleado empleado) throws BLException {
        validarExiste(empleado.getId());
        validarDatos(empleado);
        validarDniUnico(empleado);
        try {
            empleadoDAO.update(empleado);
        } catch (SQLException e) {
            throw new BLException("No se pudo actualizar el empleado", e);
        }
    }

    @Override
    public void delete(Integer id) throws BLException {
        try {
            empleadoDAO.delete(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo eliminar el empleado", e);
        }
    }

    private void validarDatos(Empleado empleado) throws BLException {
        if (!empleado.getDni().matches("\\d{8}")) {
            throw new BLException("El DNI debe tener exactamente 8 dígitos");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate nacimiento = empleado.getFechaNacimiento();
        if (nacimiento.isAfter(hoy)) {
            throw new BLException("La fecha de nacimiento no puede ser futura");
        }
        if (Period.between(nacimiento, hoy).getYears() < EDAD_MINIMA) {
            throw new BLException(
                    "El empleado debe tener al menos " + EDAD_MINIMA + " años");
        }

        if (empleado.getSueldo() < SUELDO_MINIMO) {
            throw new BLException(String.format(
                    "El sueldo no puede ser menor a la remuneración mínima (S/ %.2f)",
                    SUELDO_MINIMO));
        }
    }

    private void validarExiste(int id) throws BLException {
        try {
            if (empleadoDAO.findById(id) == null) {
                throw new BLException("No existe un empleado con id " + id);
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la existencia del empleado", e);
        }
    }

    private void validarDniUnico(Empleado empleado) throws BLException {
        try {
            Empleado existente = empleadoDAO.findByDni(empleado.getDni());
            if (existente != null && existente.getId() != empleado.getId()) {
                throw new BLException(
                        "Ya existe un empleado con el DNI " + empleado.getDni());
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la unicidad del DNI", e);
        }
    }
}
