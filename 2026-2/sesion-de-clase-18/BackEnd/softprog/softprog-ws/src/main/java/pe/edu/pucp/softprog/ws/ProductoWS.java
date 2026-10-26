package pe.edu.pucp.softprog.ws;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ProductoBL;
import pe.edu.pucp.softprog.bl.impl.ProductoBLImpl;
import pe.edu.pucp.softprog.modelo.almacen.Producto;
import pe.edu.pucp.softprog.ws.dto.ProductoDTO;
import pe.edu.pucp.softprog.ws.mapper.ProductoMapper;

import java.util.List;

@WebService(serviceName = "ProductoWS", targetNamespace = "http://ws.softprog.pucp.edu.pe/producto")
public class ProductoWS {
    private final ProductoBL productoBL = new ProductoBLImpl();

    @WebMethod(operationName = "findAllProductos")
    public List<ProductoDTO> findAllProductos() throws BLException {
        return ProductoMapper.toDTOs(productoBL.findAll());
    }

    @WebMethod(operationName = "findProductoById")
    public ProductoDTO findProductoById(@WebParam(name = "id") int id) throws BLException {
        Producto producto = productoBL.findById(id);
        return producto != null ? ProductoMapper.toDTO(producto) : null;
    }

    @WebMethod(operationName = "insertProducto")
    public int insertProducto(@WebParam(name = "producto") ProductoDTO dto) throws BLException {
        Producto producto = ProductoMapper.toModelo(dto);
        productoBL.insert(producto);
        return producto.getId();
    }

    @WebMethod(operationName = "updateProducto")
    public void updateProducto(@WebParam(name = "producto") ProductoDTO dto) throws BLException {
        productoBL.update(ProductoMapper.toModelo(dto));
    }

    @WebMethod(operationName = "deleteProducto")
    public void deleteProducto(@WebParam(name = "id") int id) throws BLException {
        productoBL.delete(id);
    }

    @WebMethod(operationName = "filterProductosByName")
    public List<ProductoDTO> filterProductosByName(@WebParam(name = "nombre") String nombre) throws BLException {
        return ProductoMapper.toDTOs(productoBL.filterByName(nombre));
    }
}
