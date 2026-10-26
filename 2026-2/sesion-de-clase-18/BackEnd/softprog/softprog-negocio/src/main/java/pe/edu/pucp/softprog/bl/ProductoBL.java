package pe.edu.pucp.softprog.bl;

import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.util.List;

public interface ProductoBL extends RegistroBL<Producto, Integer> {
    List<Producto> filterByName(String nombre) throws BLException;
}
