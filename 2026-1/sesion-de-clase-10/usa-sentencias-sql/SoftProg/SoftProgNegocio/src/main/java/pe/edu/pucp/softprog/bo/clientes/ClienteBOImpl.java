package pe.edu.pucp.softprog.bo.clientes;

import pe.edu.pucp.softprog.bo.PersonaBOImpl;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAO;
import pe.edu.pucp.softprog.dao.clientes.ClienteDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.clientes.Cliente;

import java.util.List;
import java.util.Objects;

public class ClienteBOImpl extends PersonaBOImpl<Cliente> implements ClienteBO {
    private final ClienteDAO clienteDao;

    public ClienteBOImpl() {
        this.clienteDao = new ClienteDAOImpl();
    }

    @Override
    public List<Cliente> listar() {
        return this.clienteDao.leerTodos();
    }

    @Override
    public Cliente obtener(int id) {
        validarIdPositivo(id, "id");
        return this.clienteDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        validarIdPositivo(id, "id");
        if (!this.clienteDao.eliminar(id)) {
            throw new IllegalStateException("No se pudo eliminar el cliente con id: " + id);
        }
    }

    @Override
    public void guardar(Cliente modelo, Estado estado) {
        validarCliente(modelo);
        validarEstado(estado);

        if (estado == Estado.Nuevo) {
            int id = this.clienteDao.crear(modelo);
            if (id <= 0) {
                throw new IllegalStateException("No se pudo crear el cliente");
            }
            modelo.setId(id);
        }
        else if (estado == Estado.Modificado) {
            validarIdPositivo(modelo.getId(), "id del cliente");
            if (!this.clienteDao.actualizar(modelo)) {
                throw new IllegalStateException("No se pudo actualizar el cliente con id: " + modelo.getId());
            }
        }
        else {
            throw new IllegalArgumentException("Estado no soportado en guardar: " + estado);
        }
    }

    private void validarCliente(Cliente modelo) {
        validarPersonaBasica(modelo, "cliente");
        Objects.requireNonNull(modelo.getCategoria(), "La categoria del cliente es obligatoria");
        if (modelo.getLineaCredito() < 0) {
            throw new IllegalArgumentException("La linea de credito no puede ser negativa");
        }
    }
}

