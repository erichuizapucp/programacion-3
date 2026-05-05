using System.Data;
using System.Data.Common;
using SoftProgModelo.Modelos.Ventas;
using SoftProgModelo.Modelos.Rrhh;
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
                AdjuntarTransaccionActiva(cmdEliminar);
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
                AdjuntarTransaccionActiva(cmdEliminar);
                cmdEliminar.ExecuteNonQuery();
            }

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
        if (lineas.Count == 0)
        {
            return;
        }

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
}
