namespace SoftProgDBManager.Db;

public sealed class MySqlDbManagerFactory : DbManagerFactory
{
    public override DbManager CrearDbManager(string connectionStringBase, string? usuario, string? passwordCifrado)
    {
        return MySqlDbManager.GetInstance(connectionStringBase, usuario, passwordCifrado);
    }
}
