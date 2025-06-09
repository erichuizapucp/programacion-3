package pe.edu.pucp.inf30.softprog.bo.transaccion;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import pe.edu.pucp.inf30.softprog.config.DBManager;

/**
 *
 * @author eric
 */
public abstract class TransaccionalBO {
    public void ejecutarTransaccion(
            IComandoTransaccional operacion) 
    {
        Connection conexion = null;
        
        try {
            conexion = DBManager.getInstance()
                    .getConnection();
            conexion.setAutoCommit(false);

            operacion.ejecutar(conexion);
            
            conexion.commit();
        }
        catch (SQLException | ClassNotFoundException 
                | IOException ex) {
            if (conexion != null) {
                try {
                    conexion.rollback();
                }
                catch (SQLException innerException) {
                    ex.addSuppressed(innerException);
                }
            }
            throw new RuntimeException(ex);
        }
        finally {
            if (conexion != null) {
                try {
                    conexion.close();
                }
                catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}
