using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Clientes;
using SoftProgPersistencia.Dao.Clientes;

namespace SoftProgNegocio.Bo.Clientes;

public class ClienteBoImpl : PersonaBoImpl<Cliente>, IClienteBo
{
    private readonly IClienteDao _clienteDao = new ClienteDaoImpl();

    public override List<Cliente> Listar() => _clienteDao.LeerTodos();

    public override Cliente? Obtener(int id)
    {
        ValidarIdPositivo(id, "id");
        return _clienteDao.Leer(id);
    }

    public override void Eliminar(int id)
    {
        ValidarIdPositivo(id, "id");
        if (!_clienteDao.Eliminar(id))
        {
            throw new InvalidOperationException($"No se pudo eliminar el cliente con id: {id}");
        }
    }

    public override void Guardar(Cliente modelo, Estado estado)
    {
        ValidarPersonaBasica(modelo, "cliente");
        if (modelo.LineaCredito < 0)
        {
            throw new ArgumentException("La linea de credito no puede ser negativa");
        }

        if (estado == Estado.Nuevo)
        {
            var id = _clienteDao.Crear(modelo);
            if (id <= 0)
            {
                throw new InvalidOperationException("No se pudo crear el cliente");
            }
            modelo.Id = id;
            return;
        }

        if (estado == Estado.Modificado)
        {
            ValidarIdPositivo(modelo.Id, "id del cliente");
            if (!_clienteDao.Actualizar(modelo))
            {
                throw new InvalidOperationException($"No se pudo actualizar el cliente con id: {modelo.Id}");
            }
            return;
        }

        throw new ArgumentException($"Estado no soportado en guardar: {estado}");
    }
}
