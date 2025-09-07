package pe.edu.pucp.inf30.softprog.daoimpl.rrhh;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import pe.edu.pucp.inf30.softprog.modelo.rrhh.Empleado;

/**
 *
 * @author eric
 */
public class EmpleadoDAOImpl extends BaseDAO<Empleado> {

    @Override
    protected PreparedStatement comandoCrear(Connection conn, 
            Empleado modelo) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected PreparedStatement comandoActualizar(Connection conn, 
            Empleado modelo) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected PreparedStatement comandoEliminar(Connection conn, 
            Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected PreparedStatement comandoLeer(Connection conn, 
            Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected PreparedStatement comandoLeerTodos(
            Connection conn) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Empleado mapearModelo(ResultSet rs) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
