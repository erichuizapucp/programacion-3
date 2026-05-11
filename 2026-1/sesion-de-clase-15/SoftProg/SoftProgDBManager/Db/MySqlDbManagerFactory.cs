namespace SoftProgDBManager.Db;

public sealed class MySqlDbManagerFactory : DbManagerFactory
{
    public override DbManager CrearDbManager(string connectionStringBase)
    {
        return MySqlDbManager.GetInstance(connectionStringBase);
    }
}
