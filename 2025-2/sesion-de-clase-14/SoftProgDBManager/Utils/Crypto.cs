using System;
using System.Security.Cryptography;
using System.Text;

namespace PUCP.SoftProg.Db.Utils {
    public static class Crypto {
        // Esta clave está fija solo con el propósito académico
        // En un ambiente productivo esta clave debe gestionarse
        // y almacenarse de forma segura por el departamento de IT
        private static readonly string KEY = "claveprog3202502";

        public static string Encrypt(string textoPlano) {
            using (Aes aes = Aes.Create()) {
                aes.Key = Encoding.UTF8.GetBytes(KEY);
                aes.Mode = CipherMode.ECB;
                aes.Padding = PaddingMode.PKCS7;

                ICryptoTransform encryptor = aes.CreateEncryptor();
                byte[] plainBytes = Encoding.UTF8.GetBytes(textoPlano);
                byte[] encrypted = encryptor.TransformFinalBlock(plainBytes, 0, plainBytes.Length);

                return Convert.ToBase64String(encrypted);
            }
        }

        public static string Decrypt(string textCifrado) {
            using (Aes aes = Aes.Create()) {
                aes.Key = Encoding.UTF8.GetBytes(KEY);
                aes.Mode = CipherMode.ECB;
                aes.Padding = PaddingMode.PKCS7;

                ICryptoTransform decryptor = aes.CreateDecryptor();
                byte[] cipherBytes = Convert.FromBase64String(textCifrado);
                byte[] decrypted = decryptor.TransformFinalBlock(cipherBytes, 0, cipherBytes.Length);

                return Encoding.UTF8.GetString(decrypted);
            }
        }

        public static void Main() {
            string password = "prog320252";
            string encrypted = Encrypt(password);
            string decrypted = Decrypt(encrypted);

            Console.WriteLine($"Texto plano: {password}");
            Console.WriteLine($"Cifrado: {encrypted}");
            Console.WriteLine($"Descifrado: {decrypted}");
        }
    }
}
