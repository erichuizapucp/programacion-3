using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Rrhh;
using SoftProgPersistencia.Dao.Rrhh;

namespace SoftProgNegocio.Bo.Rrhh;

public class EmpleadoBoImpl : PersonaBoImpl<Empleado>, IEmpleadoBo
{
    private readonly IEmpleadoDao _empleadoDao = new EmpleadoDaoImpl();

    public override List<Empleado> Listar() => _empleadoDao.LeerTodos();

    public override Empleado? Obtener(int id)
    {
        ValidarIdPositivo(id, "id");
        return _empleadoDao.Leer(id);
    }

    public override void Eliminar(int id)
    {
        ValidarIdPositivo(id, "id");
        if (!_empleadoDao.Eliminar(id))
        {
            throw new InvalidOperationException($"No se pudo eliminar el empleado con id: {id}");
        }
    }

    public override void Guardar(Empleado modelo, Estado estado)
    {
        ValidarPersonaBasica(modelo, "empleado");
        _ = modelo.Area ?? throw new ArgumentException("El area del empleado es obligatoria");
        if (modelo.Sueldo < 0)
        {
            throw new ArgumentException("El sueldo no puede ser negativo");
        }

        if (estado == Estado.Nuevo)
        {
            var id = _empleadoDao.Crear(modelo);
            if (id <= 0)
            {
                throw new InvalidOperationException("No se pudo crear el empleado");
            }
            modelo.Id = id;
            return;
        }

        if (estado == Estado.Modificado)
        {
            ValidarIdPositivo(modelo.Id, "id del empleado");
            if (!_empleadoDao.Actualizar(modelo))
            {
                throw new InvalidOperationException($"No se pudo actualizar el empleado con id: {modelo.Id}");
            }
            return;
        }

        throw new ArgumentException($"Estado no soportado en guardar: {estado}");
    }
}
