using SoftProgModelo.Modelos;
using SoftProgModelo.Modelos.Ventas;
using SoftProgPersistencia.Dao.Ventas;

namespace SoftProgNegocio.Bo.Ventas;

public class OrdenVentaBoImpl : BaseBo, IOrdenVentaBo
{
    private readonly IOrdenVentaDao _ordenVentaDao = new OrdenVentaDaoImpl();

    public List<OrdenVenta> Listar() => _ordenVentaDao.LeerTodos();

    public OrdenVenta? Obtener(int id)
    {
        ValidarIdPositivo(id, "id");
        return _ordenVentaDao.Leer(id);
    }

    public void Eliminar(int id)
    {
        ValidarIdPositivo(id, "id");
        if (!_ordenVentaDao.Eliminar(id))
        {
            throw new InvalidOperationException($"No se pudo eliminar la orden de venta con id: {id}");
        }
    }

    public void Guardar(OrdenVenta modelo, Estado estado)
    {
        ValidarOrdenVenta(modelo);

        if (estado == Estado.Nuevo)
        {
            var id = _ordenVentaDao.Crear(modelo);
            if (id <= 0)
            {
                throw new InvalidOperationException("No se pudo crear la orden de venta");
            }
            modelo.Id = id;
            return;
        }

        if (estado == Estado.Modificado)
        {
            ValidarIdPositivo(modelo.Id, "id de la orden de venta");
            if (!_ordenVentaDao.Actualizar(modelo))
            {
                throw new InvalidOperationException($"No se pudo actualizar la orden de venta con id: {modelo.Id}");
            }
            return;
        }

        throw new ArgumentException($"Estado no soportado en guardar: {estado}");
    }

    private void ValidarOrdenVenta(OrdenVenta modelo)
    {
        _ = modelo ?? throw new ArgumentNullException(nameof(modelo));
        _ = modelo.Cliente ?? throw new ArgumentException("El cliente de la orden es obligatorio");
        ValidarIdPositivo(modelo.Cliente.Id, "id de cliente");

        if (modelo.Empleado is not null)
        {
            ValidarIdPositivo(modelo.Empleado.Id, "id de empleado");
        }

        if (modelo.Lineas.Count == 0)
        {
            throw new ArgumentException("La orden debe tener al menos una linea");
        }

        var totalLineas = 0d;
        foreach (var linea in modelo.Lineas)
        {
            _ = linea.Producto ?? throw new ArgumentException("El producto de cada linea es obligatorio");
            ValidarIdPositivo(linea.Producto.Id, "id de producto");
            if (linea.Cantidad <= 0)
            {
                throw new ArgumentException("La cantidad de cada linea debe ser mayor a 0");
            }
            if (linea.SubTotal < 0)
            {
                throw new ArgumentException("El subtotal de cada linea no puede ser negativo");
            }
            totalLineas += linea.SubTotal;
        }

        if (Math.Abs(totalLineas - modelo.Total) > 0.01d)
        {
            throw new ArgumentException("El total de la orden no coincide con la suma de lineas");
        }
    }
}
