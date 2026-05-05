using System.Security.Cryptography;
using System.Text;

namespace SoftProgDBManager.Db.Utils;

public static class Crypto
{
    private const string Key = "claveprog3202601";

    public static string Encrypt(string textoPlano)
    {
        using var aes = Aes.Create();
        aes.Key = Encoding.UTF8.GetBytes(Key);
        aes.Mode = CipherMode.ECB;
        aes.Padding = PaddingMode.PKCS7;

        using var encryptor = aes.CreateEncryptor();
        var inputBytes = Encoding.UTF8.GetBytes(textoPlano);
        var encrypted = encryptor.TransformFinalBlock(inputBytes, 0, inputBytes.Length);
        return Convert.ToBase64String(encrypted);
    }

    public static string Decrypt(string textoCifrado)
    {
        using var aes = Aes.Create();
        aes.Key = Encoding.UTF8.GetBytes(Key);
        aes.Mode = CipherMode.ECB;
        aes.Padding = PaddingMode.PKCS7;

        using var decryptor = aes.CreateDecryptor();
        var inputBytes = Convert.FromBase64String(textoCifrado);
        var decrypted = decryptor.TransformFinalBlock(inputBytes, 0, inputBytes.Length);
        return Encoding.UTF8.GetString(decrypted);
    }

    public static void Main(string[] args)
    {
        var password = "prog320261";
        var cifrado = Encrypt(password);

        Console.WriteLine($"Password cifrado: {cifrado}");
    }
}
