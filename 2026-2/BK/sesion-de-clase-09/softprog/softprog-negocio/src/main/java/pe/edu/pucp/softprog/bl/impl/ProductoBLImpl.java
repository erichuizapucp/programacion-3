package pe.edu.pucp.softprog.bl.impl;

import pe.edu.pucp.softprog.bl.BLException;
import pe.edu.pucp.softprog.bl.ProductoBL;
import pe.edu.pucp.softprog.dao.ProductoDAO;
import pe.edu.pucp.softprog.dao.impl.ProductoDAOImpl;
import pe.edu.pucp.softprog.modelo.almacen.Producto;

import java.sql.SQLException;
import java.util.List;

public class ProductoBLImpl implements ProductoBL {
    private final ProductoDAO productoDAO = new ProductoDAOImpl();

    @Override
    public List<Producto> findAll() throws BLException {
        try {
            return productoDAO.findAll();
        } catch (SQLException e) {
            throw new BLException("No se pudo listar los productos", e);
        }
    }

    @Override
    public Producto findById(Integer id) throws BLException {
        try {
            return productoDAO.findById(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo recuperar el producto", e);
        }
    }

    @Override
    public void insert(Producto producto) throws BLException {
        validarPrecio(producto);
        validarNombreUnico(producto);
        try {
            productoDAO.insert(producto);
        } catch (SQLException e) {
            throw new BLException("No se pudo registrar el producto", e);
        }
    }

    @Override
    public void update(Producto producto) throws BLException {
        validarExiste(producto.getId());
        validarPrecio(producto);
        validarNombreUnico(producto);
        try {
            productoDAO.update(producto);
        } catch (SQLException e) {
            throw new BLException("No se pudo actualizar el producto", e);
        }
    }

    @Override
    public void delete(Integer id) throws BLException {
        try {
            productoDAO.delete(id);
        } catch (SQLException e) {
            throw new BLException("No se pudo eliminar el producto", e);
        }
    }

    private void validarPrecio(Producto producto) throws BLException {
        if (producto.getPrecio() <= 0) {
            throw new BLException("El precio del producto debe ser mayor a 0");
        }
    }

    private void validarExiste(int id) throws BLException {
        try {
            if (productoDAO.findById(id) == null) {
                throw new BLException("No existe un producto con id " + id);
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la existencia del producto", e);
        }
    }

    private void validarNombreUnico(Producto producto) throws BLException {
        try {
            Producto existente = productoDAO.findByName(producto.getNombre());
            if (existente != null && existente.getId() != producto.getId()) {
                throw new BLException(
                        "Ya existe un producto con el nombre '" + producto.getNombre() + "'");
            }
        } catch (SQLException e) {
            throw new BLException("No se pudo verificar la unicidad del nombre del producto", e);
        }
    }
}
