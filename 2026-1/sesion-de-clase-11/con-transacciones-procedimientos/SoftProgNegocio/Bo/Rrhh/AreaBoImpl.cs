using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgPersistencia.Dao.Rrhh;

namespace SoftProgNegocio.Bo.Rrhh;

public class AreaBoImpl : BaseBo, IAreaBo
{
    private readonly IAreaDao _areaDao = new AreaDaoImpl();

    public List<Area> Listar() => _areaDao.LeerTodos();

    public Area? Obtener(int id)
    {
        ValidarIdPositivo(id, "id");
        return _areaDao.Leer(id);
    }

    public void Eliminar(int id)
    {
        ValidarIdPositivo(id, "id");
        if (!_areaDao.Eliminar(id))
        {
            throw new InvalidOperationException($"No se pudo eliminar el area con id: {id}");
        }
    }

    public void Guardar(Area modelo, Estado estado)
    {
        _ = modelo ?? throw new ArgumentNullException(nameof(modelo));
        ValidarTextoObligatorio(modelo.Nombre, "nombre del area");

        if (estado == Estado.Nuevo)
        {
            var id = _areaDao.Crear(modelo);
            if (id <= 0)
            {
                throw new InvalidOperationException("No se pudo crear el area");
            }
            modelo.Id = id;
            return;
        }

        if (estado == Estado.Modificado)
        {
            ValidarIdPositivo(modelo.Id, "id del area");
            if (!_areaDao.Actualizar(modelo))
            {
                throw new InvalidOperationException($"No se pudo actualizar el area con id: {modelo.Id}");
            }
            return;
        }

        throw new ArgumentException($"Estado no soportado en guardar: {estado}");
    }
}
