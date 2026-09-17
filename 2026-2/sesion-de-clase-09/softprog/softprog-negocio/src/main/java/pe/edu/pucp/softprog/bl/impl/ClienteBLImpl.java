package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.dao.ClienteDAO;
import pe.edu.pucp.softprog.dao.impl.ClienteDAOImpl;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ClienteBLImpl implements ClienteBL {
    private static final int EDAD_MINIMA = 18;

    private final ClienteDAO clienteDAO = new ClienteDAOImpl();

    @Override
    public List<Cliente> findAll() throws BLException {
        try {
            return clienteDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar los clientes", e);
        }
    }

    @Override
    public Cliente findById(Integer id) throws BLException {
        try {
            return clienteDAO.findById(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el cliente", e);
        }
    }

    @Override
    public void insert(Cliente cliente) throws BLException {
        validarDatos(cliente);
        validarLineaCredito(cliente);
        validarDniUnico(cliente);
        try {
            clienteDAO.insert(cliente);
        } catch (SQLException e) {
            throw new BLException("No se pudo registrar el cliente", e);
        }
    }

    @Override
    public void update(Cliente cliente) throws BLException {
        validarExiste(cliente.getId());
        validarDatos(cliente);
        validarLineaCredito(cliente);
        validarDniUnico(cliente);
        try {
            clienteDAO.update(cliente);
        } catch (SQLException e) {
            throw new BLException("No se pudo actualizar el cliente", e);
        }
    }

    @Override
    public void delete(Integer id) throws BLException {
        try {
            clienteDAO.delete(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo eliminar el cliente", e);
        }
    }

    private void validarDatos(Cliente cliente) throws BLException {
        if (!cliente.getDni().matches("\\d{8}")) {
            throw new BLException("El DNI debe tener exactamente 8 dígitos");
        }

        LocalDate hoy = LocalDate.now();
        LocalDate nacimiento = cliente.getFechaNacimiento();
        if (nacimiento.isAfter(hoy)) {
            throw new BLException("La fecha de nacimiento no puede ser futura");
        }
        if (Period.between(nacimiento, hoy).getYears() < EDAD_MINIMA) {
            throw new BLException(
                    "El cliente debe tener al menos " + EDAD_MINIMA + " años");
        }
    }

    private void validarLineaCredito(Cliente cliente) throws BLException {
        double tope = switch (cliente.getCategoria()) {
            case ORO -> 20_000.0;
            case PLATA -> 10_000.0;
            case BRONCE -> 3_000.0;
        };
        if (cliente.getLineaCredito() > tope) {
            throw new BLException(String.format(
                    "La línea de crédito de un cliente %s no puede exceder S/ %.2f",
                    cliente.getCategoria(), tope));
        }
    }

    private void validarExiste(int id) throws BLException {
        try {
            if (clienteDAO.findById(id) == null) {
                throw new BLException("No existe un cliente con id " + id);
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la existencia del cliente", e);
        }
    }

    private void validarDniUnico(Cliente cliente) throws BLException {
        try {
            Cliente existente = clienteDAO.findByDni(cliente.getDni());
            if (existente != null && existente.getId() != cliente.getId()) {
                throw new BLException(
                        "Ya existe un cliente con el DNI " + cliente.getDni());
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la unicidad del DNI", e);
        }
    }
}
