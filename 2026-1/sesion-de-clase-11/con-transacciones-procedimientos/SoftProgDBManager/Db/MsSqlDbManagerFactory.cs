namespace SoftProgDBManager.Db;

public sealed class MsSqlDbManagerFactory : DbManagerFactory
{
    public override DbManager CrearDbManager(string connectionStringBase, string? usuario, string? passwordCifrado)
    {
        return MsSqlDbManager.GetInstance(connectionStringBase, usuario, passwordCifrado);
    }
}
