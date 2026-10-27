package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ClienteBL;
import pe.edu.pucp.softprog.bl.CuentaUsuarioBL;
import pe.edu.pucp.softprog.dao.ClienteDAO;
import pe.edu.pucp.softprog.dao.CuentaUsuarioDAO;
import pe.edu.pucp.softprog.dao.impl.ClienteDAOImpl;
import pe.edu.pucp.softprog.dao.impl.CuentaUsuarioDAOImpl;
import pe.edu.pucp.softprog.modelo.seguridad.CuentaUsuario;
import pe.edu.pucp.softprog.modelo.ventas.CategoriaCliente;
import pe.edu.pucp.softprog.modelo.ventas.Cliente;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class ClienteBLImpl implements ClienteBL {
    private static final int EDAD_MINIMA = 18;

    private final ClienteDAO clienteDAO = new ClienteDAOImpl();
    private final CuentaUsuarioDAO cuentaUsuarioDAO = new CuentaUsuarioDAOImpl();
    private final CuentaUsuarioBL cuentaUsuarioBL = new CuentaUsuarioBLImpl();

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
    public List<Cliente> filterByName(String nombre) throws BLException {
        try {
            return clienteDAO.filterByName(nombre);
        } catch (SQLException e) {
            throw new BLException("No se pudo filtrar los clientes por nombre", e);
        }
    }

    @Override
    public Cliente findByCuenta(String userName) throws BLException {
        try {
            return clienteDAO.findByCuenta(userName);
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el cliente de la cuenta", e);
        }
    }

    @Override
    public void registrar(Cliente cliente) throws BLException {
        CuentaUsuario cuentaUsuario = cliente.getCuentaUsuario();
        if (cuentaUsuario == null) {
            throw new BLException("El cliente debe tener una cuenta de usuario");
        }

        cliente.setCategoria(CategoriaCliente.BRONCE);
        cliente.setLineaCredito(lineaCreditoInicial(cliente.getCategoria()));
        validarDatos(cliente);
        validarLineaCredito(cliente);
        validarDniUnico(cliente);

        cuentaUsuarioBL.insert(cuentaUsuario);
        cliente.setCuentaUsuario(cuentaUsuario);
        try {
            clienteDAO.insert(cliente);
        } catch (SQLException e) {
            cuentaUsuarioBL.delete(cuentaUsuario.getId());
            throw new BLException("No se pudo registrar el cliente", e);
        }
    }

    @Override
    public void insert(Cliente cliente) throws BLException {
        cliente.setLineaCredito(lineaCreditoInicial(cliente.getCategoria()));
        validarDatos(cliente);
        validarLineaCredito(cliente);
        validarDniUnico(cliente);
        if (cliente.getCuentaUsuario() != null) {
            cliente.setCuentaUsuario(buscarCuentaUsuario(cliente.getCuentaUsuario().getId()));
        }
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
        if (cliente.getCuentaUsuario() != null) {
            cliente.setCuentaUsuario(buscarCuentaUsuario(cliente.getCuentaUsuario().getId()));
        }
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

    private double lineaCreditoInicial(CategoriaCliente categoria) {
        return switch (categoria) {
            case ORO -> 10_000.0;
            case PLATA -> 5_000.0;
            case BRONCE -> 1_500.0;
        };
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

    private CuentaUsuario buscarCuentaUsuario(int idCuentaUsuario) throws BLException {
        try {
            CuentaUsuario cuentaUsuario = cuentaUsuarioDAO.findById(idCuentaUsuario);
            if (cuentaUsuario == null) {
                throw new BLException("No existe la cuenta de usuario con id " + idCuentaUsuario);
            }
            return cuentaUsuario;
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar la cuenta de usuario", e);
        }
    }
}
