using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Ventas;
using SoftProgPersistencia.Dao.Almacen;
using SoftProgPersistencia.Dao.Clientes;
using SoftProgPersistencia.Dao.Rrhh;

namespace SoftProgPersistencia.Dao.Ventas;

public class OrdenVentaDaoImpl : DefaultBaseDao<OrdenVenta>, IOrdenVentaDao
{
    public override int Crear(OrdenVenta modelo)
    {
        return EjecutarComando(conn =>
        {
            var idOrden = EjecutarComandoCrear(conn, modelo);
            if (idOrden <= 0)
            {
                return 0;
            }

            CrearLineas(conn, idOrden, modelo.Lineas);
            return idOrden;
        });
    }

    public override bool Actualizar(OrdenVenta modelo)
    {
        return EjecutarComando(conn =>
        {
            if (!EjecutarComandoActualizar(conn, modelo))
            {
                return false;
            }

            using (var cmdEliminar = ComandoEliminarLineas(conn, modelo.Id))
            {
                cmdEliminar.ExecuteNonQuery();
            }

            CrearLineas(conn, modelo.Id, modelo.Lineas);
            return true;
        });
    }

    public override bool Eliminar(int id)
    {
        return EjecutarComando(conn =>
        {
            using (var cmdEliminar = ComandoEliminarLineas(conn, id))
            {
                cmdEliminar.ExecuteNonQuery();
            }

            return EjecutarComandoEliminar(conn, id);
        });
    }

    public override OrdenVenta? Leer(int id)
    {
        return EjecutarComando(conn =>
        {
            OrdenVenta? orden;
            using (var cmd = ComandoLeer(conn, id))
            {
                using var reader = cmd.ExecuteReader();
                if (!reader.Read())
                {
                    return null;
                }

                orden = MapearModelo(reader);
            }

            orden.Lineas = LeerLineas(conn, orden.Id);
            return orden;
        });
    }

    public override List<OrdenVenta> LeerTodos()
    {
        return EjecutarComando(conn =>
        {
            var ordenes = new List<OrdenVenta>();

            using (var cmd = ComandoLeerTodos(conn))
            {
                using var reader = cmd.ExecuteReader();
                while (reader.Read())
                {
                    var orden = MapearModelo(reader);
                    ordenes.Add(orden);
                }
            }

            foreach (var orden in ordenes)
            {
                orden.Lineas = LeerLineas(conn, orden.Id);
            }

            return ordenes;
        });
    }

    protected override DbCommand ComandoCrear(DbConnection conn, OrdenVenta modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "INSERT INTO ORDENVENTA (idCliente, idEmpleado, total, activo) VALUES (@p1, @p2, @p3, @p4);";
        CrearParametro(cmd, "@p1", modelo.Cliente?.Id ?? throw new InvalidOperationException("Cliente es obligatorio"));
        CrearParametro(cmd, "@p2", modelo.Empleado?.Id, DbType.Int32);
        CrearParametro(cmd, "@p3", modelo.Total);
        CrearParametro(cmd, "@p4", modelo.Activo);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, OrdenVenta modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = @"UPDATE ORDENVENTA SET idCliente = @p1, idEmpleado = @p2, total = @p3, activo = @p4
                            WHERE id = @p5;";
        CrearParametro(cmd, "@p1", modelo.Cliente?.Id ?? throw new InvalidOperationException("Cliente es obligatorio"));
        CrearParametro(cmd, "@p2", modelo.Empleado?.Id, DbType.Int32);
        CrearParametro(cmd, "@p3", modelo.Total);
        CrearParametro(cmd, "@p4", modelo.Activo);
        CrearParametro(cmd, "@p5", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "DELETE FROM ORDENVENTA WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM ORDENVENTA WHERE id = @p1;";
        CrearParametro(cmd, "@p1", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM ORDENVENTA;";
        return cmd;
    }

    private DbCommand ComandoCrearLinea(DbConnection conn, int idOrdenVenta, LineaOrdenVenta linea)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = @"INSERT INTO LINEAORDENVENTA (idOrdenVenta, idProducto, cantidad, subTotal, activo)
                            VALUES (@p1, @p2, @p3, @p4, @p5);";
        CrearParametro(cmd, "@p1", idOrdenVenta);
        CrearParametro(cmd, "@p2", linea.Producto?.Id ?? throw new InvalidOperationException("Producto es obligatorio"));
        CrearParametro(cmd, "@p3", linea.Cantidad);
        CrearParametro(cmd, "@p4", linea.SubTotal);
        CrearParametro(cmd, "@p5", linea.Activo);
        return cmd;
    }

    private DbCommand ComandoLeerLineas(DbConnection conn, int idOrdenVenta)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "SELECT * FROM LINEAORDENVENTA WHERE idOrdenVenta = @p1;";
        CrearParametro(cmd, "@p1", idOrdenVenta);
        return cmd;
    }

    private DbCommand ComandoEliminarLineas(DbConnection conn, int idOrdenVenta)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "DELETE FROM LINEAORDENVENTA WHERE idOrdenVenta = @p1;";
        CrearParametro(cmd, "@p1", idOrdenVenta);
        return cmd;
    }

    protected override OrdenVenta MapearModelo(DbDataReader reader)
    {
        var orden = new OrdenVenta
        {
            Id = reader.GetInt32(reader.GetOrdinal("id")),
            Total = reader.GetDouble(reader.GetOrdinal("total")),
            Activo = reader.GetBoolean(reader.GetOrdinal("activo")),
            Cliente = new ClienteDaoImpl().Leer(reader.GetInt32(reader.GetOrdinal("idCliente")))
        };

        var idEmpleado = LeerEnteroNullable(reader, "idEmpleado");
        if (idEmpleado.HasValue)
        {
            orden.Empleado = new EmpleadoDaoImpl().Leer(idEmpleado.Value);
        }

        return orden;
    }

    private LineaOrdenVenta MapearLinea(DbDataReader reader)
    {
        return new LineaOrdenVenta
        {
            Id = reader.GetInt32(reader.GetOrdinal("id")),
            Producto = new ProductoDaoImpl().Leer(reader.GetInt32(reader.GetOrdinal("idProducto"))),
            Cantidad = reader.GetInt32(reader.GetOrdinal("cantidad")),
            SubTotal = reader.GetDouble(reader.GetOrdinal("subTotal")),
            Activo = reader.GetBoolean(reader.GetOrdinal("activo"))
        };
    }

    private void CrearLineas(DbConnection conn, int idOrdenVenta, List<LineaOrdenVenta> lineas)
    {
        if (lineas.Count == 0)
        {
            return;
        }

        foreach (var linea in lineas)
        {
            using var cmd = ComandoCrearLinea(conn, idOrdenVenta, linea);
            _ = cmd.ExecuteNonQuery();
        }
    }

    private List<LineaOrdenVenta> LeerLineas(DbConnection conn, int idOrdenVenta)
    {
        using var cmd = ComandoLeerLineas(conn, idOrdenVenta);
        using var reader = cmd.ExecuteReader();
        var lineas = new List<LineaOrdenVenta>();
        while (reader.Read())
        {
            lineas.Add(MapearLinea(reader));
        }

        return lineas;
    }
}
