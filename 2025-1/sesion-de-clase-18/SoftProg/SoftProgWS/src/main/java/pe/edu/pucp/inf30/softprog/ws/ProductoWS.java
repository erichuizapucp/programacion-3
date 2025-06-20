package pe.edu.pucp.inf30.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.inf30.softprog.bo.Estado;
import pe.edu.pucp.inf30.softprog.bo.IProductoBO;
import pe.edu.pucp.inf30.softprog.boimpl.ProductoBOImpl;
import pe.edu.pucp.inf30.softprog.model.logistica.almacen.Producto;

/**
 *
 * @author eric
 */
@WebService(serviceName = "ProductoWS", targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class ProductoWS {
    private final IProductoBO productoBO;
    
    public ProductoWS() {
        this.productoBO = new ProductoBOImpl();
    }
    
    @WebMethod(operationName = "listarProductos")
    public List<Producto> listarProductos() {
        return this.productoBO.listar();
    }
    
    @WebMethod(operationName = "obtenerProducto")
    public Producto obtenerProducto(
        @WebParam(name = "id") int id
    ) {
        return this.productoBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarProducto")
    public void eliminarProducto(
        @WebParam(name = "id") int id
    ) {
        this.productoBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarProducto")
    public void guardarProducto(
        @WebParam(name = "producto") Producto producto, 
        @WebParam(name = "estado") Estado estado
    ) {
        this.productoBO.guardar(producto, estado);
    }
}
