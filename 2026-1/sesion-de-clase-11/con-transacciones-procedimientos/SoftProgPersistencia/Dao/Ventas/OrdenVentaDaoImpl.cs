using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Rrhh;
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

            EliminarLineasPorOrden(conn, modelo.Id);
            CrearLineas(conn, modelo.Id, modelo.Lineas);
            return true;
        });
    }

    public override bool Eliminar(int id)
    {
        return EjecutarComando(conn =>
        {
            EliminarLineasPorOrden(conn, id);
            return EjecutarComandoEliminar(conn, id);
        });
    }

    public override OrdenVenta? Leer(int id)
    {
        var orden = new OrdenVenta();
        return EjecutarComando(conn =>
        {
            var existe = false;
            var idOrden = 0;
            var idCliente = 0;
            int? idEmpleado = null;
            var total = 0d;
            var activo = false;

            using (var cmd = ComandoLeer(conn, id))
            {
                AdjuntarTransaccionActiva(cmd);
                using (var reader = cmd.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        existe = true;
                        idOrden = LeerEntero(reader, "id");
                        idCliente = LeerEntero(reader, "idCliente");
                        idEmpleado = LeerEnteroNullable(reader, "idEmpleado");
                        total = LeerDecimal(reader, "total");
                        activo = LeerBooleano(reader, "activo");
                    }
                }

                if (!existe)
                {
                    return null;
                }

                orden = new OrdenVenta
                {
                    Id = idOrden,
                    Total = total,
                    Activo = activo,
                    Cliente = new ClienteDaoImpl().Leer(idCliente)
                        ?? throw new InvalidOperationException($"No existe el cliente con id {idCliente}")
                };

                if (idEmpleado.HasValue)
                {
                    orden.Empleado = new EmpleadoDaoImpl().Leer(idEmpleado.Value);
                }
            }

            orden.Lineas = LeerLineas(conn, idOrden);
            return orden;
        });
    }

    public override List<OrdenVenta> LeerTodos()
    {
        return EjecutarComando(conn =>
        {
            var filas = new List<(int IdOrden, int IdCliente, int? IdEmpleado, double Total, bool Activo)>();
            using var cmd = ComandoLeerTodos(conn);
            AdjuntarTransaccionActiva(cmd);
            using (var reader = cmd.ExecuteReader())
            {
                while (reader.Read())
                {
                    filas.Add((
                        LeerEntero(reader, "id"),
                        LeerEntero(reader, "idCliente"),
                        LeerEnteroNullable(reader, "idEmpleado"),
                        LeerDecimal(reader, "total"),
                        LeerBooleano(reader, "activo")));
                }
            }

            var ordenes = new List<OrdenVenta>(filas.Count);
            foreach (var (IdOrden, IdCliente, IdEmpleado, Total, Activo) in filas)
            {
                var cliente = new ClienteDaoImpl().Leer(IdCliente)
                    ?? throw new InvalidOperationException($"No existe el cliente con id {IdCliente}");

                Empleado? empleado = null;
                if (IdEmpleado.HasValue)
                {
                    empleado = new EmpleadoDaoImpl().Leer(IdEmpleado.Value);
                }

                var orden = new OrdenVenta
                {
                    Id = IdOrden,
                    Total = Total,
                    Activo = Activo,
                    Cliente = cliente,
                    Empleado = empleado,
                    Lineas = LeerLineas(conn, IdOrden)
                };

                ordenes.Add(orden);
            }

            return ordenes;
        });
    }

    protected override DbCommand ComandoCrear(DbConnection conn, OrdenVenta modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "insertarOrdenVenta";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_idCliente", modelo.Cliente?.Id ?? throw new InvalidOperationException("Cliente es obligatorio"));
        CrearParametro(cmd, "@p_idEmpleado", modelo.Empleado?.Id, DbType.Int32);
        CrearParametro(cmd, "@p_total", modelo.Total);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "p_id", DbType.Int32);
        return cmd;
    }

    protected override DbCommand ComandoActualizar(DbConnection conn, OrdenVenta modelo)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "modificarOrdenVenta";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_idCliente", modelo.Cliente?.Id ?? throw new InvalidOperationException("Cliente es obligatorio"));
        CrearParametro(cmd, "@p_idEmpleado", modelo.Empleado?.Id, DbType.Int32);
        CrearParametro(cmd, "@p_total", modelo.Total);
        CrearParametro(cmd, "@p_activo", modelo.Activo);
        CrearParametro(cmd, "@p_id", modelo.Id);
        return cmd;
    }

    protected override DbCommand ComandoEliminar(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "eliminarOrdenVenta";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeer(DbConnection conn, int id)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "buscarOrdenVentaPorId";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", id);
        return cmd;
    }

    protected override DbCommand ComandoLeerTodos(DbConnection conn)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "listarOrdenesVenta";
        cmd.CommandType = CommandType.StoredProcedure;
        return cmd;
    }

    private DbCommand ComandoCrearLinea(DbConnection conn, int idOrdenVenta, LineaOrdenVenta linea)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "insertarLineaOrdenVenta";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_idOrdenVenta", idOrdenVenta);
        CrearParametro(cmd, "@p_idProducto", linea.Producto?.Id ?? throw new InvalidOperationException("Producto es obligatorio"));
        CrearParametro(cmd, "@p_cantidad", linea.Cantidad);
        CrearParametro(cmd, "@p_subTotal", linea.SubTotal);
        CrearParametro(cmd, "@p_activo", linea.Activo);
        CrearParametro(cmd, "p_id", DbType.Int32);
        return cmd;
    }

    private DbCommand ComandoLeerLineas(DbConnection conn, int idOrdenVenta)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "listarLineasPorOrdenVenta";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_idOrdenVenta", idOrdenVenta);
        return cmd;
    }

    private DbCommand ComandoEliminarLinea(DbConnection conn, int idLineaOrdenVenta)
    {
        var cmd = conn.CreateCommand();
        cmd.CommandText = "eliminarLineaOrdenVenta";
        cmd.CommandType = CommandType.StoredProcedure;
        CrearParametro(cmd, "@p_id", idLineaOrdenVenta);
        return cmd;
    }

    protected override OrdenVenta MapearModelo(DbDataReader reader)
    {
        var orden = new OrdenVenta
        {
            Id = LeerEntero(reader, "id"),
            Total = LeerDecimal(reader, "total"),
            Activo = LeerBooleano(reader, "activo"),
            Cliente = new ClienteDaoImpl().Leer(LeerEntero(reader, "idCliente"))
        };

        var idEmpleado = LeerEnteroNullable(reader, "idEmpleado");
        if (idEmpleado.HasValue)
        {
            orden.Empleado = new EmpleadoDaoImpl().Leer(idEmpleado.Value);
        }

        return orden;
    }

    private void CrearLineas(DbConnection conn, int idOrdenVenta, List<LineaOrdenVenta> lineas)
    {
        foreach (var linea in lineas)
        {
            using var cmd = ComandoCrearLinea(conn, idOrdenVenta, linea);
            AdjuntarTransaccionActiva(cmd);
            _ = cmd.ExecuteNonQuery();
        }
    }

    private List<LineaOrdenVenta> LeerLineas(DbConnection conn, int idOrdenVenta)
    {
        var filas = new List<(int Id, int IdProducto, int Cantidad, double SubTotal, bool Activo)>();
        using (var cmd = ComandoLeerLineas(conn, idOrdenVenta))
        {
            AdjuntarTransaccionActiva(cmd);
            using var reader = cmd.ExecuteReader();
            while (reader.Read())
            {
                filas.Add((
                    LeerEntero(reader, "id"),
                    LeerEntero(reader, "idProducto"),
                    LeerEntero(reader, "cantidad"),
                    LeerDecimal(reader, "subTotal"),
                    LeerBooleano(reader, "activo")));
            }
        }

        var lineas = new List<LineaOrdenVenta>(filas.Count);
        foreach (var (Id, IdProducto, Cantidad, SubTotal, Activo) in filas)
        {
            lineas.Add(new LineaOrdenVenta
            {
                Id = Id,
                Producto = new ProductoDaoImpl().Leer(IdProducto),
                Cantidad = Cantidad,
                SubTotal = SubTotal,
                Activo = Activo
            });
        }

        return lineas;
    }

    private void EliminarLineasPorOrden(DbConnection conn, int idOrdenVenta)
    {
        var ids = new List<int>();
        using (var cmdLeer = ComandoLeerLineas(conn, idOrdenVenta))
        {
            AdjuntarTransaccionActiva(cmdLeer);
            using var reader = cmdLeer.ExecuteReader();
            while (reader.Read())
            {
                ids.Add(LeerEntero(reader, "id"));
            }
        }

        foreach (var id in ids)
        {
            using var cmdEliminar = ComandoEliminarLinea(conn, id);
            AdjuntarTransaccionActiva(cmdEliminar);
            _ = cmdEliminar.ExecuteNonQuery();
        }
    }
}
