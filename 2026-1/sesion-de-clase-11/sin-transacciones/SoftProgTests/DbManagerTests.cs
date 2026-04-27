using SoftProgDBManager.Db.Utils;

namespace SoftProgTests;

public class DbManagerTests
{
    [Fact]
    public void CryptoEncryptDecryptShouldRoundtrip()
    {
        const string plain = "prog320261";
        var encrypted = Crypto.Encrypt(plain);
        var decrypted = Crypto.Decrypt(encrypted);

        Assert.Equal(plain, decrypted);
    }

    [Fact]
    public void CadenaConexionShouldBuildMySqlFormat()
    {
        var cadena = new CadenaConexion
        {
            TipoDb = TipoDb.MySQL,
            Servidor = "localhost",
            Puerto = 3306,
            Schema = "softprog"
        };

        Assert.Contains("Server=localhost", cadena.ToString());
        Assert.Contains("Database=softprog", cadena.ToString());
    }
}
