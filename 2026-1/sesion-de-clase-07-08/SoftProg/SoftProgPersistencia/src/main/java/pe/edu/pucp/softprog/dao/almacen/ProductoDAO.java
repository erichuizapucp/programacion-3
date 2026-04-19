package pe.edu.pucp.softprog.dao.almacen;

import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.util.List;

public interface ProductoDAO {
    Integer crear(Producto modelo);
    boolean actualizar(Producto modelo);
    boolean eliminar(int id);
    Producto leer(int id);
    List<Producto> leerTodos();
}
