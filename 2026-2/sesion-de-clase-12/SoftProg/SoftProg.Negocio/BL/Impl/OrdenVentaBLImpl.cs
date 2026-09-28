using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.AccessoDatos.DAO.Impl.Ventas;
using SoftProg.AccessoDatos.DAO.Transacciones;
using SoftProg.Modelo.Almacen;
using SoftProg.Modelo.Ventas;

namespace SoftProg.Negocio.BL.Impl {
    public class OrdenVentaBLImpl : IOrdenVentaBL {
        private readonly IOrdenVentaDAO ordenVentaDAO = new OrdenVentaDAOImpl();
        private readonly IProductoDAO productoDAO = new ProductoDAOImpl();

        public List<OrdenVenta> FindAll() {
            try {
                return ordenVentaDAO.FindAll();
            } catch (Exception ex) {
                throw new BLException("No se pudo listar las órdenes de venta", ex);
            }
        }

        public OrdenVenta? FindById(int id) {
            try {
                return ordenVentaDAO.FindById(id);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar la orden de venta", ex);
            }
        }

        public void Insert(OrdenVenta ordenVenta) {
            ValidarYCalcular(ordenVenta);

            TransactionsManager.Iniciar();
            try {
                ordenVentaDAO.Insert(ordenVenta);
                TransactionsManager.Commit();
            } catch (Exception ex) {
                TransactionsManager.Rollback();
                throw new BLException("No se pudo registrar la orden de venta", ex);
            }
        }

        public void Update(OrdenVenta ordenVenta) {
            ValidarYCalcular(ordenVenta);

            TransactionsManager.Iniciar();
            try {
                ordenVentaDAO.Update(ordenVenta);
                TransactionsManager.Commit();
            } catch (Exception ex) {
                TransactionsManager.Rollback();
                throw new BLException("No se pudo actualizar la orden de venta", ex);
            }
        }

        public void Delete(int id) {
            TransactionsManager.Iniciar();
            try {
                ordenVentaDAO.Delete(id);
                TransactionsManager.Commit();
            } catch (Exception ex) {
                TransactionsManager.Rollback();
                throw new BLException("No se pudo eliminar la orden de venta", ex);
            }
        }

        private void ValidarYCalcular(OrdenVenta ordenVenta) {
            if (ordenVenta.Lineas.Count == 0) {
                throw new BLException("La orden de venta debe tener al menos una línea");
            }

            Cliente? cliente = ordenVenta.Cliente;
            if (cliente == null) {
                throw new BLException("La orden de venta debe tener un cliente");
            }

            List<LineaOrdenVenta> lineasCalculadas = [];
            double total = 0.0;
            foreach (LineaOrdenVenta linea in ordenVenta.Lineas) {
                if (linea.Cantidad < 1) {
                    throw new BLException("La cantidad de cada línea debe ser al menos 1");
                }

                Producto producto = BuscarProducto(linea.Producto.Id);
                double subTotal = producto.Precio * linea.Cantidad;

                LineaOrdenVenta lineaCalculada = new(linea);
                lineaCalculada.SubTotal = subTotal;
                lineasCalculadas.Add(lineaCalculada);
                total += subTotal;
            }

            double lineaCredito = cliente.LineaCredito;
            if (lineaCredito > 0 && total > lineaCredito) {
                throw new BLException(
                    $"El total de la orden (S/ {total:F2}) excede la línea de crédito del cliente (S/ {lineaCredito:F2})");
            }

            ordenVenta.Lineas = lineasCalculadas;
            ordenVenta.Total = total;
        }

        private Producto BuscarProducto(int idProducto) {
            Producto? producto;
            try {
                producto = productoDAO.FindById(idProducto);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar el producto de la línea", ex);
            }

            if (producto == null) {
                throw new BLException($"No existe un producto con id {idProducto}");
            }
            return producto;
        }
    }
}
