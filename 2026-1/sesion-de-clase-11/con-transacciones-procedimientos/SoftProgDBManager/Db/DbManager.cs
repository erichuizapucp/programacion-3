using System.Data.Common;
using SoftProgDBManager.Db.Utils;

namespace SoftProgDBManager.Db;

public abstract class DbManager
{
    protected string ConnectionStringBase { get; }
    protected string? Usuario { get; }
    protected string? Password { get; }

    protected DbManager(string connectionStringBase, string? usuario, string? passwordCifrado)
    {
        ConnectionStringBase = connectionStringBase;
        Usuario = string.IsNullOrWhiteSpace(usuario) ? null : usuario;
        Password = ResolvePassword(passwordCifrado);
    }

    public abstract DbConnection GetConnection();

    private static string? ResolvePassword(string? passwordCifrado)
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
