using System.Data.Common;
using SoftProgDBManager.Db.Utils;

namespace SoftProgDBManager.Db;

public abstract class DbManager
{
    protected string ConnectionStringBase { get; }

    protected DbManager(string connectionStringBase)
    {
        ConnectionStringBase = connectionStringBase;
    }

    public abstract DbConnection GetConnection();

    protected static string? ResolvePassword(string? passwordCifrado)
    {
        if (string.IsNullOrWhiteSpace(passwordCifrado))
        {
            return null;
        }

        try
        {
            return Crypto.Decrypt(passwordCifrado);
        }
        catch (FormatException)
        {
            // Permite usar password en texto plano si no viene cifrado.
            return passwordCifrado;
        }
    }
}
