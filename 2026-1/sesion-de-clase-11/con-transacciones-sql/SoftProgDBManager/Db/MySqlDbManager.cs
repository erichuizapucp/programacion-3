using System.Data.Common;
using MySqlConnector;

namespace SoftProgDBManager.Db;

public sealed class MySqlDbManager : DbManager
{
    private static MySqlDbManager? _instancia;

    private MySqlDbManager(string connectionStringBase, string? usuario, string? passwordCifrado)
        : base(connectionStringBase, usuario, passwordCifrado)
    {
    }

    public static MySqlDbManager GetInstance(string connectionStringBase, string? usuario, string? passwordCifrado)
    {
        _instancia ??= new MySqlDbManager(connectionStringBase, usuario, passwordCifrado);
        return _instancia;
    }

    public override DbConnection GetConnection()
    {
        var builder = new MySqlConnectionStringBuilder(ConnectionStringBase);

        if (!string.IsNullOrWhiteSpace(Usuario))
        {
            builder.UserID = Usuario;
        }

        if (!string.IsNullOrWhiteSpace(Password))
        {
            builder.Password = Password;
        }

        return new MySqlConnection(builder.ConnectionString);
    }
}
