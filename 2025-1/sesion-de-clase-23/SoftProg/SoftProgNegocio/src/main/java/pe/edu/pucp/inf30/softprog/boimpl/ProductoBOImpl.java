package pe.edu.pucp.inf30.softprog.boimpl;

import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.IProductoBO;
import pe.edu.pucp.inf30.softprog.dao.logistica.almacen.IProductoDAO;
import pe.edu.pucp.inf30.softprog.daoimpl.logistica.almacen.ProductoDAOImpl;
import pe.edu.pucp.inf30.softprog.model.Estado;
import pe.edu.pucp.inf30.softprog.model.logistica.almacen.Producto;

/**
 *
 * @author eric
 */
public class ProductoBOImpl implements IProductoBO {
    private final IProductoDAO productoDao;

    public ProductoBOImpl() {
        this.productoDao = new ProductoDAOImpl();
    }
    
    @Override
    public List<Producto> listar() {
        return this.productoDao.listar();
    }

    @Override
    public Producto obtener(int id) {
        return this.productoDao.buscar(id);
    }

    @Override
    public void eliminar(int id) {
        this.productoDao.eliminar(id);
    }

    @Override
    public void guardar(Producto modelo, Estado estado) {
        if (estado == Estado.Nuevo) {
            this.productoDao.insertar(modelo);
        }
        else {
            this.productoDao.modificar(modelo);
        }
    }
}
