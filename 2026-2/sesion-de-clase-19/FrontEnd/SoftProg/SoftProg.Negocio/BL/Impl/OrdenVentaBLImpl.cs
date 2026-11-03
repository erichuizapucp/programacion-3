using SoftProg.AccessoDatos.DAO;
using SoftProg.AccessoDatos.DAO.Impl;
using SoftProg.AccessoDatos.DAO.Impl.Ventas;
using SoftProg.AccessoDatos.DAO.Transacciones;
using SoftProg.Modelo.Almacen;
using SoftProg.Modelo.Ventas;

namespace SoftProg.Negocio.BL.Impl {
    public class OrdenVentaBLImpl : IOrdenVentaBL {
        private const double TasaIgv = 0.18;

        private readonly IOrdenVentaDAO ordenVentaDAO = new OrdenVentaDAOImpl();
        private readonly IProductoDAO productoDAO = new ProductoDAOImpl();
        private readonly IClienteDAO clienteDAO = new ClienteDAOImpl();

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

        public List<OrdenVenta> FindByCuenta(string userName) {
            try {
                return ordenVentaDAO.FindByCuenta(userName);
            } catch (Exception ex) {
                throw new BLException("No se pudo listar las órdenes de venta de la cuenta", ex);
            }
        }

        public void Insert(OrdenVenta ordenVenta) {
            ValidarYCalcular(ordenVenta);
            Cliente cliente = BuscarCliente(ordenVenta.Cliente!.Id);
            ValidarLineaCredito(ordenVenta.Total, cliente.LineaCredito);

            TransactionsManager.Iniciar();
            try {
                ordenVentaDAO.Insert(ordenVenta);
                clienteDAO.UpdateLineaCredito(cliente.Id, cliente.LineaCredito - ordenVenta.Total);
                TransactionsManager.Commit();
            } catch (Exception ex) {
                TransactionsManager.Rollback();
                throw new BLException("No se pudo registrar la orden de venta", ex);
            }
        }

        public void Update(OrdenVenta ordenVenta) {
            ValidarYCalcular(ordenVenta);
            OrdenVenta ordenAnterior = BuscarOrden(ordenVenta.Id);
            Cliente clienteAnterior = BuscarCliente(ordenAnterior.Cliente!.Id);
            Cliente clienteNuevo = BuscarCliente(ordenVenta.Cliente!.Id);

            double lineaCreditoAnteriorDevuelta = clienteAnterior.LineaCredito + ordenAnterior.Total;
            double lineaCreditoDisponible;
            if (clienteNuevo.Id == clienteAnterior.Id) {
                lineaCreditoDisponible = lineaCreditoAnteriorDevuelta;
            } else {
                lineaCreditoDisponible = clienteNuevo.LineaCredito;
            }
            ValidarLineaCredito(ordenVenta.Total, lineaCreditoDisponible);

            TransactionsManager.Iniciar();
            try {
                ordenVentaDAO.Update(ordenVenta);
                clienteDAO.UpdateLineaCredito(clienteAnterior.Id, lineaCreditoAnteriorDevuelta);
                clienteDAO.UpdateLineaCredito(clienteNuevo.Id, lineaCreditoDisponible - ordenVenta.Total);
                TransactionsManager.Commit();
            } catch (Exception ex) {
                TransactionsManager.Rollback();
                throw new BLException("No se pudo actualizar la orden de venta", ex);
            }
        }

        public void Delete(int id) {
            OrdenVenta ordenAnterior = BuscarOrden(id);
            Cliente cliente = BuscarCliente(ordenAnterior.Cliente!.Id);

            TransactionsManager.Iniciar();
            try {
                ordenVentaDAO.Delete(id);
                clienteDAO.UpdateLineaCredito(cliente.Id, cliente.LineaCredito + ordenAnterior.Total);
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
            double subTotalOrden = 0.0;
            foreach (LineaOrdenVenta linea in ordenVenta.Lineas) {
                if (linea.Cantidad < 1) {
                    throw new BLException("La cantidad de cada línea debe ser al menos 1");
                }

                Producto producto = BuscarProducto(linea.Producto.Id);
                double subTotal = producto.Precio * linea.Cantidad;

                LineaOrdenVenta lineaCalculada = new(linea);
                lineaCalculada.SubTotal = subTotal;
                lineasCalculadas.Add(lineaCalculada);
                subTotalOrden += subTotal;
            }

            double igv = Math.Round(subTotalOrden * TasaIgv, 2);
            double total = subTotalOrden + igv;

            ordenVenta.Lineas = lineasCalculadas;
            ordenVenta.Total = total;
        }

        private void ValidarLineaCredito(double total, double lineaCreditoDisponible) {
            if (total > lineaCreditoDisponible) {
                throw new BLException(
                    $"El total de la orden (S/ {total:F2}) excede la línea de crédito disponible del cliente (S/ {lineaCreditoDisponible:F2})");
            }
        }

        private OrdenVenta BuscarOrden(int idOrden) {
            OrdenVenta? ordenVenta;
            try {
                ordenVenta = ordenVentaDAO.FindById(idOrden);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar la orden de venta", ex);
            }

            if (ordenVenta == null) {
                throw new BLException($"No existe la orden de venta con id {idOrden}");
            }
            return ordenVenta;
        }

        private Cliente BuscarCliente(int idCliente) {
            Cliente? cliente;
            try {
                cliente = clienteDAO.FindById(idCliente);
            } catch (Exception ex) {
                throw new BLException("No se pudo recuperar el cliente", ex);
            }

            if (cliente == null) {
                throw new BLException($"No existe el cliente con id {idCliente}");
            }
            return cliente;
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
