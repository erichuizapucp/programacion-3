package pe.edu.pucp.softprog.dao;

import java.util.List;

/**
 * Interfaz genérica para operaciones CRUD.
 *
 * @param <M> tipo del modelo
 * @param <I> tipo del identificador
 *
 * <p>Ejemplos de uso:
 * <pre>{@code
 * // identificador entero
 * Persistible<Producto, Integer> repoProducto;
 *
 * // identificador como cadena
 * Persistible<Usuario, String> repoUsuario;
 *
 * // identificador compuesto: combinación de dos campos
 * // Ejemplo de clase identificador:
 * // public final class IdCompuesto {
 * //     private final int parteA;
 * //     private final String parteB;
 * //     public IdCompuesto(int parteA, String parteB) { ... }
 * //     // equals/hashCode basados en parteA y parteB
 * // }
 *
 * // Entidad de ejemplo con IdCompuesto
 * // public class Entidad { private IdCompuesto id; private String nombre; // getters/setters }
 *
 * // Implementación DAO mínima:
 * // public class EntidadDAO implements Persistible<Entidad, IdCompuesto> {
 * //     public IdCompuesto crear(Entidad modelo) { // persistir y devolver id
 * //         return null;
 * //     }
 * //     public boolean actualizar(Entidad modelo) { return false; }
 * //     public boolean eliminar(IdCompuesto id) { return false; }
 * //     public Entidad leer(IdCompuesto id) { return null; }
 * //     public java.util.List<Entidad> leerTodos() { return java.util.Collections.emptyList(); }
 * // }
 * }</pre>
 */
public interface Persistible<M, I> {
    /** Persiste {@code modelo} y devuelve su identificador. */
    I crear(M modelo);

    /** Actualiza {@code modelo}. Devuelve {@code true} si se actualizó correctamente. */
    boolean actualizar(M modelo);

    /** Elimina la entidad identificada por {@code id}. Devuelve {@code true} si se eliminó. */
    boolean eliminar(I id);

    /** Devuelve la entidad identificada por {@code id}, o {@code null} si no existe. */
    M leer(I id);

    /** Devuelve todas las instancias del modelo. */
    List<M> leerTodos();
}
