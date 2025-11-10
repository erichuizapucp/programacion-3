package pe.edu.pucp.inf30.softprog.db.utils;

/**
 *
 * @author eric
 */
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class Crypto {
    // Esta clave esta fija solo con el proposito academico
    // de mostrar la encriptacion de una contrasena
    // en un ambiente productivo esta clave debe ser gestionada
    // y almacenada de forma segura por el departamento de IT
    private static final String KEY = "claveprog3202502"; 

    public static String encrypt(String textoPlano) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encrypted = cipher.doFinal(textoPlano.getBytes());
        return Base64.getEncoder().encodeToString(encrypted);
    }

    public static String decrypt(String textCifrado) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] decoded = Base64.getDecoder().decode(textCifrado);
        byte[] decrypted = cipher.doFinal(decoded);
        return new String(decrypted);
    }

    public static void main(String[] args) throws Exception {
        String password = "prog320252";
        System.out.println(encrypt(password));
    }
}
