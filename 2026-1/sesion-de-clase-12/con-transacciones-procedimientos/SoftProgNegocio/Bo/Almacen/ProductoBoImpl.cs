using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Almacen;
using SoftProgPersistencia.Dao.Almacen;

namespace SoftProgNegocio.Bo.Almacen;

public class ProductoBoImpl : BaseBo, IProductoBo
{
    private readonly IProductoDao _productoDao = new ProductoDaoImpl();

    public List<Producto> Listar() => _productoDao.LeerTodos();

    public Producto? Obtener(int id)
    {
        ValidarIdPositivo(id, "id");
        return _productoDao.Leer(id);
    }

    public void Eliminar(int id)
    {
        ValidarIdPositivo(id, "id");
        if (!_productoDao.Eliminar(id))
        {
            throw new InvalidOperationException($"No se pudo eliminar el producto con id: {id}");
        }
    }

    public void Guardar(Producto modelo, Estado estado)
    {
        _ = modelo ?? throw new ArgumentNullException(nameof(modelo));
        ValidarTextoObligatorio(modelo.Nombre, "nombre del producto");
        if (modelo.Precio < 0)
        {
            throw new ArgumentException("El precio no puede ser negativo");
        }

        if (estado == Estado.Nuevo)
        {
            var id = _productoDao.Crear(modelo);
            if (id <= 0)
            {
                throw new InvalidOperationException("No se pudo crear el producto");
            }
            modelo.Id = id;
            return;
        }

        if (estado == Estado.Modificado)
        {
            ValidarIdPositivo(modelo.Id, "id del producto");
            if (!_productoDao.Actualizar(modelo))
            {
                throw new InvalidOperationException($"No se pudo actualizar el producto con id: {modelo.Id}");
            }
            return;
        }

        throw new ArgumentException($"Estado no soportado en guardar: {estado}");
    }
}
