package pe.edu.pucp.softprog.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Interfaz funcional que representa una operación/consulta a ejecutar sobre
 * una conexión {@link Connection} y que devuelve un resultado de tipo {@code R}.
 *
 * <p>Es funcional: puede usarse con expresiones lambda o referencias a métodos.
 * Se utiliza desde {@code BaseDAO.ejecutarComando(...)}. Ejemplos:
 * <pre>{@code
 * // desde BaseDAO.crear(modelo):
 * return ejecutarComando(conn -> ejecutarComandoCrear(conn, modelo));
 *
 * // ejemplo directo de uso con SQL y retorno del id generado:
 * ComandoDAO<Integer> crearCmd = conn -> {
 *     try (PreparedStatement ps = conn.prepareStatement(
 *             "INSERT ...", java.sql.Statement.RETURN_GENERATED_KEYS)) {
 *         // set parámetros
 *         ps.executeUpdate();
 *         try (ResultSet rs = ps.getGeneratedKeys()) {
 *             return rs.next() ? rs.getInt(1) : null;
 *         }
 *     }
 * };
 * Integer id = crearCmd.ejecutar(conn);
 * }</pre>
 *
 * <p>La firma permite propagar {@link SQLException} al llamador; en
 * {@code BaseDAO} estas excepciones se capturan y transforman en RuntimeException.
 *
 * @param <R> tipo de resultado devuelto por la operación
 */
@FunctionalInterface
public interface ComandoDAO<R> {
    /** Ejecuta la operación sobre {@code conn} y devuelve el resultado. */
    R ejecutar(Connection conn) throws SQLException;
}
