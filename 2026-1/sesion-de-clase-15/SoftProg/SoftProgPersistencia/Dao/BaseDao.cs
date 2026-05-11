using System.Data;
using System.Data.Common;
using SoftProgDBManager.Db;

namespace SoftProgPersistencia.Dao;

public abstract class BaseDao<M, I> : IPersistible<M, I>
{
    public virtual I Crear(M modelo)
    {
        return EjecutarComando(conn => EjecutarComandoCrear(conn, modelo));
    }

    public virtual bool Actualizar(M modelo)
    {
        return EjecutarComando(conn => EjecutarComandoActualizar(conn, modelo));
    }

    public virtual bool Eliminar(I id)
    {
        return EjecutarComando(conn => EjecutarComandoEliminar(conn, id));
    }

    public virtual M? Leer(I id)
    {
        return EjecutarComando(conn =>
        {
            using var cmd = ComandoLeer(conn, id);
            AdjuntarTransaccionActiva(cmd);
            using var reader = cmd.ExecuteReader();
            return reader.Read() ? MapearModelo(reader) : default;
        });
    }

    public virtual List<M> LeerTodos()
    {
        return EjecutarComando(conn =>
        {
            using var cmd = ComandoLeerTodos(conn);
            AdjuntarTransaccionActiva(cmd);
            using var reader = cmd.ExecuteReader();
            var list = new List<M>();
            while (reader.Read())
            {
                list.Add(MapearModelo(reader));
            }
            return list;
        });
    }

    protected T EjecutarComando<T>(ComandoDao<T> comando)
    {
        var txConnection = TransactionsManager.ObtenerConexionActual();
        if (txConnection is not null)
        {
            return comando(txConnection);
        }

        using var conn = DbFactoryProvider.GetManager().GetConnection();
        conn.Open();
        return comando(conn);
    }

    protected virtual I EjecutarComandoCrear(DbConnection conn, M modelo)
    {
        using var cmd = ComandoCrear(conn, modelo);
        AdjuntarTransaccionActiva(cmd);
        var rows = cmd.ExecuteNonQuery();
        if (rows == 0)
        {
            return default!;
        }
        return ExtraerIdDespuesDeCrear(cmd, conn);
    }

    protected virtual bool EjecutarComandoActualizar(DbConnection conn, M modelo)
    {
        using var cmd = ComandoActualizar(conn, modelo);
        AdjuntarTransaccionActiva(cmd);
        return cmd.ExecuteNonQuery() > 0;
    }

    protected virtual bool EjecutarComandoEliminar(DbConnection conn, I id)
    {
        using var cmd = ComandoEliminar(conn, id);
        AdjuntarTransaccionActiva(cmd);
        return cmd.ExecuteNonQuery() > 0;
    }

    protected void AdjuntarTransaccionActiva(DbCommand command)
    {
        var tx = TransactionsManager.ObtenerTransaccionActual();
        if (tx is not null)
        {
            command.Transaction = tx;
        }
    }

    protected DbParameter CrearParametro(DbCommand cmd, string name, object? value, DbType? dbType = null)
    {
        return CrearParametro(cmd, name, value, ParameterDirection.Input, dbType);
    }

    protected DbParameter CrearParametro(DbCommand cmd, string name, DbType dbType)
    {
        return CrearParametro(cmd, name, DBNull.Value, ParameterDirection.Output, dbType);
    }

    protected DbParameter CrearParametro(DbCommand cmd, string name, object? value, ParameterDirection direction, DbType? dbType = null)
    {
        var parameter = cmd.CreateParameter();
        parameter.ParameterName = name;
        parameter.Direction = direction;
        parameter.Value = value ?? DBNull.Value;
        if (dbType.HasValue)
        {
            parameter.DbType = dbType.Value;
        }
        cmd.Parameters.Add(parameter);
        return parameter;
    }

    protected int LeerEntero(DbDataReader reader, string columnName)
    {
        return Convert.ToInt32(reader[columnName]);
    }

    protected string LeerTexto(DbDataReader reader, string columnName)
    {
        return Convert.ToString(reader[columnName])
            ?? throw new InvalidOperationException($"La columna {columnName} no contiene texto valido");
    }

    protected double LeerDecimal(DbDataReader reader, string columnName)
    {
        var value = reader[columnName];
        if (value is DBNull)
        {
            return 0.00;
        }
        return Convert.ToDouble(value);
    }

    protected bool LeerBooleano(DbDataReader reader, string columnName)
    {
        return Convert.ToBoolean(reader[columnName]);
    }

    protected DateTime LeerFecha(DbDataReader reader, string columnName)
    {
        return Convert.ToDateTime(reader[columnName]);
    }

    protected int? LeerEnteroNullable(DbDataReader reader, string columnName)
    {
        var value = reader[columnName];
        if (value is DBNull)
        {
            return null;
        }
        return Convert.ToInt32(value);
    }

    protected abstract DbCommand ComandoCrear(DbConnection conn, M modelo);
    protected abstract DbCommand ComandoActualizar(DbConnection conn, M modelo);
    protected abstract DbCommand ComandoEliminar(DbConnection conn, I id);
    protected abstract DbCommand ComandoLeer(DbConnection conn, I id);
    protected abstract DbCommand ComandoLeerTodos(DbConnection conn);
    protected abstract M MapearModelo(DbDataReader reader);
    protected abstract I ExtraerIdDespuesDeCrear(DbCommand cmd, DbConnection conn);
}
