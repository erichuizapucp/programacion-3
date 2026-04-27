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
            using var reader = cmd.ExecuteReader();
            return reader.Read() ? MapearModelo(reader) : default;
        });
    }

    public virtual List<M> LeerTodos()
    {
        return EjecutarComando(conn =>
        {
            using var cmd = ComandoLeerTodos(conn);
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
        using var conn = DbFactoryProvider.GetManager().GetConnection();
        conn.Open();
        return comando(conn);
    }

    protected virtual I EjecutarComandoCrear(DbConnection conn, M modelo)
    {
        using var cmd = ComandoCrear(conn, modelo);
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
        return cmd.ExecuteNonQuery() > 0;
    }

    protected virtual bool EjecutarComandoEliminar(DbConnection conn, I id)
    {
        using var cmd = ComandoEliminar(conn, id);
        return cmd.ExecuteNonQuery() > 0;
    }

    protected DbParameter CrearParametro(DbCommand cmd, string name, object? value, DbType? dbType = null)
    {
        var parameter = cmd.CreateParameter();
        parameter.ParameterName = name;
        parameter.Value = value ?? DBNull.Value;
        if (dbType.HasValue)
        {
            parameter.DbType = dbType.Value;
        }
        cmd.Parameters.Add(parameter);
        return parameter;
    }

    protected int? LeerEnteroNullable(DbDataReader reader, string columnName)
    {
        var ordinal = reader.GetOrdinal(columnName);
        if (reader.IsDBNull(ordinal))
        {
            return null;
        }
        return reader.GetInt32(ordinal);
    }

    protected double? LeerDoubleNullable(DbDataReader reader, string columnName)
    {
        var ordinal = reader.GetOrdinal(columnName);
        if (reader.IsDBNull(ordinal))
        {
            return null;
        }
        return reader.GetDouble(ordinal);
    }

    protected abstract DbCommand ComandoCrear(DbConnection conn, M modelo);
    protected abstract DbCommand ComandoActualizar(DbConnection conn, M modelo);
    protected abstract DbCommand ComandoEliminar(DbConnection conn, I id);
    protected abstract DbCommand ComandoLeer(DbConnection conn, I id);
    protected abstract DbCommand ComandoLeerTodos(DbConnection conn);
    protected abstract M MapearModelo(DbDataReader reader);
    protected abstract I ExtraerIdDespuesDeCrear(DbCommand cmd, DbConnection conn);
}
