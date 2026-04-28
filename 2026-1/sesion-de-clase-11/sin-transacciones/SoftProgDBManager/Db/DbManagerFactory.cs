namespace SoftProgDBManager.Db;

public abstract class DbManagerFactory
{
    public abstract DbManager CrearDbManager(string connectionStringBase);
}
