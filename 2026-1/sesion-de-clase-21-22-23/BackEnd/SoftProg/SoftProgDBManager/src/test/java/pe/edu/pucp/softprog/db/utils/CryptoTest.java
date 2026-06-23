package pe.edu.pucp.softprog.db.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CryptoTest {

    @Test
    void encryptYDecryptDebenSerConsistentes() throws Exception {
        String plano = "password123";

        String cifrado = Crypto.encrypt(plano);
        String descifrado = Crypto.decrypt(cifrado);

        assertNotEquals(plano, cifrado);
        assertEquals(plano, descifrado);
    }
}

