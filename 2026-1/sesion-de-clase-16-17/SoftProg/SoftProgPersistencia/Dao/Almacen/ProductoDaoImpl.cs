using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Almacen;

namespace SoftProgPersistencia.Dao.Almacen;

public class ProductoDaoImpl : DefaultBaseDao<Producto>, IProductoDao
{
    protected override DbCommand ComandoCrear(DbConnection conn, Producto modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "insertarProducto";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_nombre", modelo.Nombre);
        CrearParametro(cmd, "@p_unidadMedida", modelo.UnidadMedida.ToString());
        CrearParametro(cmd, "@p_precio", modelo.Precio);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "p_id", DbType.Int32);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Producto modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "modificarProducto";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_nombre", modelo.Nombre);
        CrearParametro(cmd, "@p_unidadMedida", modelo.UnidadMedida.ToString());
        CrearParametro(cmd, "@p_precio", modelo.Precio);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "@p_id", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "eliminarProducto";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarProductoPorId";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "listarProductos";
        cmd.CommandType = CommandType.StoredProcedure;
        return cmd;
    }

    protected override Producto MapearModelo(DbDataReader reader)
    {
        return new Producto
        {
            Id = LeerEntero(reader, "id"),
            Nombre = LeerTexto(reader, "nombre"),
            UnidadMedida = Enum.Parse<UnidadMedida>(LeerTexto(reader, "unidadMedida")),
            Precio = LeerDecimal(reader, "precio"),
            Activo = LeerBooleano(reader, "activo")
        };
    }
}
