package pe.edu.pucp.softprog.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import pe.edu.pucp.softprog.db.DBFactoryProvider;
import pe.edu.pucp.softprog.db.DBManager;

/**
 * Contrato base genérico para DAOs basados en comandos SQL.
 *
 * <p>Este contrato define cómo construir sentencias SQL y mapear resultados
 * para un modelo {@code M} con identificador {@code I}. La implementacion
 * concreta de ejecucion se delega a clases derivadas para cada tipo de id.
 *
 * @param <M> tipo del modelo/entidad manejado por el DAO
 * @param <I> tipo del identificador de la entidad
 */
public abstract class BaseDAO<M, I> implements Persistible<M, I> {
    @Override
    public I crear(M modelo) {
        return ejecutarComando(conn -> ejecutarComandoCrear(conn, modelo));
    }

    @Override
    public boolean actualizar(M modelo) {
        return ejecutarComando(conn -> ejecutarComandoActualizar(conn, modelo));
    }

    @Override
    public boolean eliminar(I id) {
        return ejecutarComando(conn -> ejecutarComandoEliminar(conn, id));
    }

    @Override
    public M leer(I id) {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoLeer(conn, id);
                 ResultSet rs = cmd.executeQuery()) {
                if (!rs.next()) {
                    System.err.println("No se encontro el registro con id: " + id);
                    return null;
                }
                return this.mapearModelo(rs);
            }
        });
    }

    @Override
    public List<M> leerTodos() {
        return ejecutarComando(conn -> {
            try (PreparedStatement cmd = this.comandoLeerTodos(conn);
                 ResultSet rs = cmd.executeQuery()) {
                List<M> modelos = new ArrayList<>();
                while (rs.next()) {
                    modelos.add(this.mapearModelo(rs));
                }
                return modelos;
            }
        });
    }

    /**
     * Ejecuta un comando SQL representado por el {@code ComandoDAO<R>} y maneja la conexión y excepciones de
     * forma centralizada. El comando se ejecuta dentro de un bloque try-with-resources para asegurar el cierre
     * adecuado de la conexión. Cualquier excepción SQL o inesperada se captura, se imprime un mensaje
     * de error y se relanza como RuntimeException para que sea manejada por capas superiores.
     * */
    protected <R> R ejecutarComando(ComandoDAO<R> command) {
        Connection txConnection = TransactionsManager.obtenerConexionActual();
        if (txConnection != null) {
            return ejecutarComandoConTransaccion(command, txConnection);
        }

        return ejecutarComandoSinTransaccion(command);
    }

    protected <R> R ejecutarComandoConTransaccion(ComandoDAO<R> command,
                                                  Connection txConnection) {
        try {
            return command.ejecutar(txConnection);
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected <R> R ejecutarComandoSinTransaccion(ComandoDAO<R> command) {
        DBManager dbManager = DBFactoryProvider.getManager();
        try (Connection conn = dbManager.getConnection()) {
            return command.ejecutar(conn);
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
        catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected I ejecutarComandoCrear(Connection conn, M modelo) {
        try (PreparedStatement cmd = this.comandoCrear(conn, modelo)) {
            if (cmd.executeUpdate() == 0) {
                return null;
            }

            if (cmd instanceof CallableStatement callableCmd) {
                return extraerIdDesdeCallable(callableCmd);
            }

            try (ResultSet rs = cmd.getGeneratedKeys()) {
                if (rs.next()) {
                    return extraerIdDesdeGeneratedKeys(rs);
                }
            }

            return null;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected boolean ejecutarComandoActualizar(Connection conn, M modelo) {
        try (PreparedStatement cmd = this.comandoActualizar(conn, modelo)) {
            return cmd.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    protected boolean ejecutarComandoEliminar(Connection conn, I id) {
        try (PreparedStatement cmd = this.comandoEliminar(conn, id)) {
            return cmd.executeUpdate() > 0;
        }
        catch (SQLException e) {
            System.err.println("Error SQL: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * Construye el {@link PreparedStatement} para la operación de creación
     * (INSERT). La implementación debe preparar la sentencia y asignar
     * los parámetros en el orden esperado por la base de datos.
     *
     * @param conn conexión abierta
     * @param modelo entidad a persistir
     * @return PreparedStatement listo para ejecutar
     * @throws SQLException en caso de error al preparar la sentencia
     */
    protected abstract PreparedStatement comandoCrear(Connection conn,
                                                      M modelo) throws SQLException;
    
    /**
     * Construye el {@link PreparedStatement} para la operación de actualización
     * (UPDATE). Debe incluir los parámetros necesarios y, normalmente, el
     * identificador de la entidad.
     */
    protected abstract PreparedStatement comandoActualizar(Connection conn,
                                                           M modelo) throws SQLException;
    
    /**
     * Construye el {@link PreparedStatement} para la operación de eliminación
     * (DELETE) a partir del identificador {@code id}.
     */
    protected abstract PreparedStatement comandoEliminar(Connection conn,
                                                         I id) throws SQLException;

    /**
     * Construye el {@link PreparedStatement} para leer una entidad por su
     * identificador {@code id}.
     */
    protected abstract PreparedStatement comandoLeer(Connection conn,
                                                     I id) throws SQLException;

    /**
     * Construye el {@link PreparedStatement} para leer todas las entidades
     * (SELECT ... sin filtro).
     */
    protected abstract PreparedStatement comandoLeerTodos(Connection conn) throws SQLException;
    
    /**
     * Mapea la fila actual del {@link ResultSet} a una instancia del modelo
     * {@code M}. Se invoca después de posicionar el cursor en la fila
     * correspondiente (p. ej. tras {@code rs.next()}).
     */
    protected abstract M mapearModelo(ResultSet rs) throws SQLException;

    /**
     * Convierte el valor del parámetro de salida {@code p_id} (CallableStatement)
     * al tipo de identificador del DAO.
     */
    protected abstract I extraerIdDesdeCallable(CallableStatement cmd) throws SQLException;

    /**
     * Convierte el valor de las generated keys al tipo de identificador del DAO.
     */
    protected abstract I extraerIdDesdeGeneratedKeys(ResultSet rs) throws SQLException;

    protected void setEnteroNullable(PreparedStatement cmd, int index,
                                     Integer value) throws SQLException {
        if (value == null) {
            cmd.setNull(index, java.sql.Types.INTEGER);
        }
        else {
            cmd.setInt(index, value);
        }
    }

    protected Integer leerEnteroNullable(ResultSet rs, String columnName) throws SQLException {
        int value = rs.getInt(columnName);
        return rs.wasNull() ? null : value;
    }
}
