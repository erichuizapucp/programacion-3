package pe.edu.pucp.softprog.db;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pe.edu.pucp.softprog.db.utils.Crypto;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class DBManagerInfrastructureTest {

    @BeforeEach
    void resetSingletons() throws Exception {
        resetSingleton(MySQLDBManager.class, "instancia");
        resetSingleton(MSSQLDBManager.class, "instancia");
        resetSingleton(DBFactoryProvider.class, "instancia");
    }

    @Test
    void mysqlFactoryDebeCrearSingleton() throws Exception {
        String encrypted = Crypto.encrypt("clave123");
        DBManagerFactory factory = new MySQLDBManagerFactory();

        DBManager m1 = factory.crearDBManager("localhost", 3306, "softprog", "root", encrypted);
        DBManager m2 = factory.crearDBManager("otrohost", 3307, "otro", "otro", encrypted);

        assertInstanceOf(MySQLDBManager.class, m1);
        assertSame(m1, m2);
    }

    @Test
    void mssqlFactoryDebeCrearSingleton() throws Exception {
        String encrypted = Crypto.encrypt("clave456");
        DBManagerFactory factory = new MSSQLDBManagerFactory();

        DBManager m1 = factory.crearDBManager("localhost", 1433, "softprog", "sa", encrypted);
        DBManager m2 = factory.crearDBManager("otrohost", 1440, "otro", "otro", encrypted);

        assertInstanceOf(MSSQLDBManager.class, m1);
        assertSame(m1, m2);
    }

    @Test
    void mysqlManagerDebeDesencriptarPasswordYConstruirCadenaConexion() throws Exception {
        String encrypted = Crypto.encrypt("miPassword");
        MySQLDBManager manager = new MySQLDBManager("localhost", 3306, "softprog", "root", encrypted);

        assertEquals("miPassword", manager.password);
        assertEquals(
                "jdbc:mysql://localhost:3306/softprog?useSSL=false&allowPublicKeyRetrieval=true",
                manager.cadenaConexion()
        );
    }

    @Test
    void mssqlManagerDebeConstruirCadenaConexion() throws Exception {
        String encrypted = Crypto.encrypt("miPassword");
        MSSQLDBManager manager = new MSSQLDBManager("localhost", 1433, "softprog", "sa", encrypted);

        assertEquals(
                "jdbc:sqlserver://localhost:1433;databaseName=softprog;encrypt=false",
                manager.cadenaConexion()
        );
    }

    private void resetSingleton(Class<?> clazz, String fieldName) throws Exception {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(null, null);
    }
}

