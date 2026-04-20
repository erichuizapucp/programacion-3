package pe.edu.pucp.softprog.bo.almacen;

import pe.edu.pucp.softprog.bo.BaseBO;
import pe.edu.pucp.softprog.dao.almacen.ProductoDAO;
import pe.edu.pucp.softprog.dao.almacen.ProductoDAOImpl;
import pe.edu.pucp.softprog.modelo.Estado;
import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.util.List;
import java.util.Objects;

public class ProductoBOImpl extends BaseBO implements ProductoBO {
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
        validarIdPositivo(id, "id");
        return this.productoDao.leer(id);
    }

    @Override
    public void eliminar(int id) {
        validarIdPositivo(id, "id");
        if (!this.productoDao.eliminar(id)) {
            throw new IllegalStateException("No se pudo eliminar el producto con id: " + id);
        }
    }

    @Override
    public void guardar(Producto modelo, Estado estado) {
        validarProducto(modelo);
        validarEstado(estado);

        if (estado == Estado.Nuevo) {
            int id = this.productoDao.crear(modelo);
            if (id <= 0) {
                throw new IllegalStateException("No se pudo crear el producto");
            }
            modelo.setId(id);
        }
        else if (estado == Estado.Modificado) {
            validarIdPositivo(modelo.getId(), "id del producto");
            if (!this.productoDao.actualizar(modelo)) {
                throw new IllegalStateException("No se pudo actualizar el producto con id: " + modelo.getId());
            }
        }
        else {
            throw new IllegalArgumentException("Estado no soportado en guardar: " + estado);
        }
    }

    private void validarProducto(Producto modelo) {
        Objects.requireNonNull(modelo, "El producto es obligatorio");
        validarTextoObligatorio(modelo.getNombre(), "nombre del producto");
        Objects.requireNonNull(modelo.getUnidadMedida(), "La unidad de medida es obligatoria");
        if (modelo.getPrecio() < 0) {
            throw new IllegalArgumentException("El precio no puede ser negativo");
        }
    }
}

