using System.Data.Common;
using SoftProgModelo.Modelos.Almacen;

namespace SoftProgPersistencia.Dao.Almacen;

public class ProductoDaoImpl : DefaultBaseDao<Producto>, IProductoDao
{
    protected override DbCommand ComandoCrear(DbConnection conn, Producto modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = @"INSERT INTO PRODUCTO (nombre, unidadMedida, precio, activo)
                            VALUES (@p1, @p2, @p3, @p4);";
        CrearParametro(cmd, "@p1", modelo.Nombre);
        CrearParametro(cmd, "@p2", modelo.UnidadMedida.ToString());
        CrearParametro(cmd, "@p3", modelo.Precio);
        CrearParametro(cmd, "@p4", modelo.Activo);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, Producto modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = @"UPDATE PRODUCTO SET nombre = @p1, unidadMedida = @p2, precio = @p3, activo = @p4
                            WHERE id = @p5;";
        CrearParametro(cmd, "@p1", modelo.Nombre);
        CrearParametro(cmd, "@p2", modelo.UnidadMedida.ToString());
        CrearParametro(cmd, "@p3", modelo.Precio);
        CrearParametro(cmd, "@p4", modelo.Activo);
        CrearParametro(cmd, "@p5", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "DELETE FROM PRODUCTO WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM PRODUCTO WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM PRODUCTO;";
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
