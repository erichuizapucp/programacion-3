using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgPersistencia.Dao.Cuentas;

namespace SoftProgNegocio.Bo.Cuentas;

public class CuentaUsuarioBoImpl : BaseBo, ICuentaUsuarioBo
{
    private readonly ICuentaUsuarioDao _cuentaUsuarioDao = new CuentaUsuarioDaoImpl();

    public bool Login(string username, string password)
    {
        ValidarTextoObligatorio(username, "username");
        ValidarTextoObligatorio(password, "password");
        return _cuentaUsuarioDao.Login(username, password);
    }

    public List<CuentaUsuario> Listar() => _cuentaUsuarioDao.LeerTodos();

    public CuentaUsuario? Obtener(int id)
    {
        ValidarIdPositivo(id, "id");
        return _cuentaUsuarioDao.Leer(id);
    }

    public void Eliminar(int id)
    {
        ValidarIdPositivo(id, "id");
        if (!_cuentaUsuarioDao.Eliminar(id))
        {
            throw new InvalidOperationException($"No se pudo eliminar la cuenta con id: {id}");
        }
    }

    public void Guardar(CuentaUsuario modelo, Estado estado)
    {
        _ = modelo ?? throw new ArgumentNullException(nameof(modelo));
        ValidarTextoObligatorio(modelo.UserName, "username");
        ValidarTextoObligatorio(modelo.Password, "password");

        if (estado == Estado.Nuevo)
        {
            var id = _cuentaUsuarioDao.Crear(modelo);
            if (id <= 0)
            {
                throw new InvalidOperationException("No se pudo crear la cuenta de usuario");
            }
            modelo.Id = id;
            return;
        }

        if (estado == Estado.Modificado)
        {
            ValidarIdPositivo(modelo.Id, "id de la cuenta");
            if (!_cuentaUsuarioDao.Actualizar(modelo))
            {
                throw new InvalidOperationException($"No se pudo actualizar la cuenta con id: {modelo.Id}");
            }
            return;
        }

        throw new ArgumentException($"Estado no soportado en guardar: {estado}");
    }
}
