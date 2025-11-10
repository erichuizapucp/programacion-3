package pe.edu.pucp.inf30.softprog.boimpl.almacen;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.almacen.ProductoBO;
import pe.edu.pucp.inf30.softprog.modelo.Estado;
import pe.edu.pucp.inf30.softprog.modelo.almacen.Producto;
import pe.edu.pucp.inf30.softprog.dao.almacen.ProductoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.almacen.ProductoDAOImpl;

/**
 *
 * @author eric
 */
public class ProductoBOImpl implements ProductoBO {
    private final ProductoDAO productoDao;

    public ProductoBOImpl() {
        this.productoDao = new ProductoDAOImpl();
    }
    
    @Override
    public List<Producto> listar() {
        return this.productoDao.leerTodos();
    }

    @Override
    public Producto obtener(int id) {
        return this.productoDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        this.productoDao.eliminar(id);
    }

    @Override
    public void guardar(Producto modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.productoDao.crear(modelo);
        }
        else {
            this.productoDao.actualizar(modelo);
        }
    }
}
