using Microsoft.Extensions.Configuration;
using SoftProgDBManager.Db.Utils;

namespace SoftProgDBManager.Db;

public static class DbFactoryProvider
{
    private static DbManager? _instancia;

    public static DbManager GetManager()
    {
        if (_instancia is not null)
        {
            return _instancia;
        }

        var config = ConfigurationContext.Current;

        var tipoRaw = config["DbSettings:Tipo"]
            ?? "MySQL";
        var tipo = Enum.Parse<TipoDb>(tipoRaw, ignoreCase: true);

        var connectionStringBase = config.GetConnectionString("SoftProgDb")
            ?? throw new InvalidOperationException("No se encontro ConnectionStrings:SoftProgDb en la configuracion.");

        var usuario = config["DbSettings:Usuario"]
            ?? string.Empty;

        var passwordCifrado = config["DbSettings:Password"]
            ?? string.Empty;

        DbManagerFactory factory = tipo switch
        {
            TipoDb.MSSQL => new MsSqlDbManagerFactory(),
            TipoDb.MySQL => new MySqlDbManagerFactory(),
            _ => throw new ArgumentOutOfRangeException(nameof(tipo), tipo, "Tipo de DB no soportado")
        };

        _instancia = factory.CrearDbManager(connectionStringBase, usuario, passwordCifrado);
        return _instancia;
    }
}
