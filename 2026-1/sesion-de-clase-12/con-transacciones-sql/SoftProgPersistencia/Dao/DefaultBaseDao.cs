using System.Data.Common;

namespace SoftProgPersistencia.Dao;

public abstract class DefaultBaseDao<M> : BaseDao<M, int>
{
    protected override int ExtraerIdDespuesDeCrear(DbCommand cmd, DbConnection conn)
    {
        if (cmd.Parameters.Contains("p_id"))
        {
            var value = cmd.Parameters["p_id"]?.Value;
            return value is null or DBNull ? 0 : Convert.ToInt32(value);
        }

        using var idCmd = conn.CreateCommand();
        idCmd.Transaction = cmd.Transaction;
        idCmd.CommandText = conn.GetType().Name.Contains("MySql", StringComparison.OrdinalIgnoreCase)
            ? "SELECT LAST_INSERT_ID();"
            : "SELECT CAST(SCOPE_IDENTITY() as int);";

        var valueObj = idCmd.ExecuteScalar();
        return valueObj is null or DBNull ? 0 : Convert.ToInt32(valueObj);
    }
}
