namespace SoftProgDBManager.Db;

public sealed class MsSqlDbManagerFactory : DbManagerFactory
{
    public override DbManager CrearDbManager(string connectionStringBase)
    {
        return MsSqlDbManager.GetInstance(connectionStringBase);
    }
}
