package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebService;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import java.util.List;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.bo.almacen.ProductoBO;
import pe.edu.pucp.softprog.bo.almacen.ProductoBOImpl;
import pe.edu.pucp.softprog.modelo.almacen.Producto;


@WebService(
        serviceName = "ProductosWS",
        targetNamespace = "http://services.softprog.pucp.edu.pe/")
public class ProductosWS {
    private final ProductoBO productoBO;
    
    public ProductosWS() {
        this.productoBO = new ProductoBOImpl();
    }
    
    @WebMethod(operationName = "listarProductos")
    public List<Producto> listarProductos() {
        return this.productoBO.listar();
    }
    
    @WebMethod(operationName = "obtenerProducto")
    public Producto obtenerProducto(@WebParam(name = "id") int id) {
        return this.productoBO.obtener(id);
    }
    
    @WebMethod(operationName = "eliminarProducto")
    public void eliminarProducto(@WebParam(name = "id") int id) {
        this.productoBO.eliminar(id);
    }
    
    @WebMethod(operationName = "guardarProducto")
    public void guardarProducto(@WebParam(name = "producto") Producto producto,
                                @WebParam(name = "estado") Estado estado) {
        this.productoBO.guardar(producto, estado);
    }
}