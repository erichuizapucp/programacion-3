using System.Data;
using System.Data.Common;
using System.Threading;
using SoftProgDBManager.Db;

namespace SoftProgPersistencia.Dao;

public static class TransactionsManager
{
    private static readonly AsyncLocal<DbConnection?> ConexionActual = new();
    private static readonly AsyncLocal<DbTransaction?> TransaccionActual = new();

    public static void IniciarTransaccion()
    {
        if (HayTransaccionActiva())
        {
            throw new InvalidOperationException("Ya existe una transaccion activa en este contexto");
        }

        var conexion = DbFactoryProvider.GetManager().GetConnection();
        conexion.Open();
        var transaccion = conexion.BeginTransaction(IsolationLevel.ReadCommitted);
        ConexionActual.Value = conexion;
        TransaccionActual.Value = transaccion;
    }

    public static void CommitTransaccion()
    {
        var transaccion = ObtenerTransaccionActiva();
        try
        {
            transaccion.Commit();
        }
        finally
        {
            CerrarRecursosActuales();
        }
    }

    public static void RollbackTransaccion()
    {
        var transaccion = ObtenerTransaccionActiva();
        try
        {
            transaccion.Rollback();
        }
        finally
        {
            CerrarRecursosActuales();
        }
    }

    public static DbConnection? ObtenerConexionActual() => ConexionActual.Value;
    public static DbTransaction? ObtenerTransaccionActual() => TransaccionActual.Value;
    public static bool HayTransaccionActiva() => ConexionActual.Value is not null && TransaccionActual.Value is not null;

    private static DbTransaction ObtenerTransaccionActiva()
    {
        return TransaccionActual.Value ?? throw new InvalidOperationException("No hay una transaccion activa en este contexto");
    }

    private static void CerrarRecursosActuales()
    {
        try
        {
            TransaccionActual.Value?.Dispose();
            ConexionActual.Value?.Dispose();
        }
        finally
        {
            TransaccionActual.Value = null;
            ConexionActual.Value = null;
        }
    }
}
