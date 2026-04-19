package pe.edu.pucp.softprog.db.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CadenaConexionTest {

    @Test
    void debeConstruirCadenaMySQL() {
        CadenaConexion cadena = new CadenaConexion.Builder()
                .tipoDB(TipoDB.MySQL)
                .servidor("localhost")
                .puerto(3306)
                .schema("softprog")
                .build();

        assertEquals(
                "jdbc:mysql://localhost:3306/softprog?useSSL=false&allowPublicKeyRetrieval=true",
                cadena.toString()
        );
    }

    @Test
    void debeConstruirCadenaMSSQL() {
        CadenaConexion cadena = new CadenaConexion.Builder()
                .tipoDB(TipoDB.MSSQL)
                .servidor("localhost")
                .puerto(1433)
                .schema("softprog")
                .build();

        assertEquals(
                "jdbc:sqlserver://localhost:1433;databaseName=softprog;encrypt=false",
                cadena.toString()
        );
    }
}

