using System.Data.Common;
using Microsoft.Data.SqlClient;

namespace SoftProgDBManager.Db;

public sealed class MsSqlDbManager : DbManager
{
    private static MsSqlDbManager? _instancia;

    private MsSqlDbManager(string connectionStringBase)
        : base(connectionStringBase)
    {
    }

    public static MsSqlDbManager GetInstance(string connectionStringBase)
    {
        _instancia ??= new MsSqlDbManager(connectionStringBase);
        return _instancia;
    }

    public override DbConnection GetConnection()
    {
        var builder = new SqlConnectionStringBuilder(ConnectionStringBase);

        if (!string.IsNullOrWhiteSpace(builder.Password))
        {
            builder.Password = ResolvePassword(builder.Password) ?? string.Empty;
        }

        return new SqlConnection(builder.ConnectionString);
    }
}
