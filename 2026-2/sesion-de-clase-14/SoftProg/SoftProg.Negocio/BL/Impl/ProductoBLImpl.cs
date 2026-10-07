using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.Modelo.Almacen;

namespace SoftProg.Negocio.BL.Impl {
    public class ProductoBLImpl : IProductoBL {
        private readonly IProductoDAO productoDAO = new ProductoDAOImpl();

        public List<Producto> FindAll() {
            try {
                return productoDAO.FindAll();
            } catch (Exception ex) {
                throw new BLException("No se pudo listar los productos", ex);
            }
        }

        public Producto? FindById(int id) {
            try {
                return productoDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar el producto", ex);
            }
        }

        public void Insert(Producto producto) {
            ValidarPrecio(producto);
            ValidarNombreUnico(producto);
            try {
                productoDAO.Insert(producto);
            } catch (Exception ex) {
                throw new BLException("No se pudo registrar el producto", ex);
            }
        }

        public void Update(Producto producto) {
            ValidarExiste(producto.Id);
            ValidarPrecio(producto);
            ValidarNombreUnico(producto);
            try {
                productoDAO.Update(producto);
            } catch (Exception ex) {
                throw new BLException("No se pudo actualizar el producto", ex);
            }
        }

        public void Delete(int id) {
            try {
                productoDAO.Delete(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo eliminar el producto", ex);
            }
        }

        private void ValidarPrecio(Producto producto) {
            if (producto.Precio <= 0) {
                throw new BLException("El precio del producto debe ser mayor a 0");
            }
        }

        private void ValidarExiste(int id) {
            Producto? existente;
            try {
                existente = productoDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la existencia del producto", ex);
            }

            if (existente == null) {
                throw new BLException($"No existe un producto con id {id}");
            }
        }

        private void ValidarNombreUnico(Producto producto) {
            Producto? existente;
            try {
                existente = productoDAO.FindByName(producto.Nombre);
            } catch (Exception ex) {
                throw new BLException("No se pudo verificar la unicidad del nombre del producto", ex);
            }

            if (existente != null && existente.Id != producto.Id) {
                throw new BLException($"Ya existe un producto con el nombre '{producto.Nombre}'");
            }
        }
    }
}
