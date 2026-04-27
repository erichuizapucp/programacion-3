using System.Data.Common;
using Microsoft.Data.SqlClient;

namespace SoftProgDBManager.Db;

public sealed class MsSqlDbManager : DbManager
{
    private static MsSqlDbManager? _instancia;

    private MsSqlDbManager(string connectionStringBase, string? usuario, string? passwordCifrado)
        : base(connectionStringBase, usuario, passwordCifrado)
    {
    }

    public static MsSqlDbManager GetInstance(string connectionStringBase, string? usuario, string? passwordCifrado)
    {
        _instancia ??= new MsSqlDbManager(connectionStringBase, usuario, passwordCifrado);
        return _instancia;
    }

    public override DbConnection GetConnection()
    {
        var builder = new SqlConnectionStringBuilder(ConnectionStringBase);

        if (!string.IsNullOrWhiteSpace(Usuario))
        {
            builder.UserID = Usuario;
        }

        if (!string.IsNullOrWhiteSpace(Password))
        {
            builder.Password = Password;
        }

        return new SqlConnection(builder.ConnectionString);
    }
}
