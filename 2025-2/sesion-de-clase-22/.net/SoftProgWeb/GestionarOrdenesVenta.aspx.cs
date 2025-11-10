using PUCP.SoftProg.Web.SoftProgWS;
using System;
using System.ComponentModel;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class GestionarOrdenesVenta : Page {
        private readonly ClienteWSClient clienteWS;
        private readonly ProductoWSClient productoWS;
        private readonly OrdenVentaWSClient ordenVentaWS;
        private BindingList<producto> productos;

        protected BindingList<lineaOrdenVenta> LineasOrden {
            get {
                if (Session["LineasOrden"] == null)
                    Session["LineasOrden"] = new BindingList<lineaOrdenVenta>();
                return (BindingList<lineaOrdenVenta>)Session["LineasOrden"];
            }
            set {
                Session["LineasOrden"] = value;
            }
        }

        public GestionarOrdenesVenta() {
            clienteWS = new ClienteWSClient();
            productoWS = new ProductoWSClient();
            ordenVentaWS = new OrdenVentaWSClient();
        }

        protected void Page_Load(object sender, EventArgs e){
            if (!IsPostBack) {
                string cuenta = Page.User.Identity.Name;
                cliente cliente = clienteWS.buscarClientePorCuenta(cuenta);

                hdnIdCliente.Value = cliente.id.ToString();
                txtDNICliente.Text = cliente.dni;
                txtNombreCliente.Text = $"{cliente.nombre} {cliente.apellidoPaterno}";
            }
            
            productos = new BindingList<producto>(productoWS.listarProductos());
            gvProductos.DataSource = productos;
            gvProductos.DataBind();
        }
        
        protected void btnBuscarProducto_Click(object sender, EventArgs e) {
            //Llamar a una función Javascript
            string script = "window.onload = function() { showModalFormProducto()};";
            ClientScript.RegisterStartupScript(
                GetType(), "", script, true);
        }

        protected void gvProductos_RowDataBound(object sender, GridViewRowEventArgs e) {
            if (e.Row.RowType == DataControlRowType.DataRow) {
            }
        }

        protected void lbSeleccionarProducto_Click(object sender, EventArgs e) {
            int idProducto = int.Parse(((LinkButton)sender).CommandArgument);
            producto producto = productos.SingleOrDefault(x => x.id == idProducto);

            txtIDProducto.Text = producto.id.ToString();
            txtNombreProducto.Text = producto.nombre + " " + producto.unidadMedida;
            txtPrecioUnitProducto.Text = producto.precio.ToString("N2");
        }

        protected void lbAgregarLOV_Click(object sender, EventArgs e) {
            if (!(Session["LineasOrdenVenta"] is BindingList<lineaOrdenVenta> lineas))
                lineas = new BindingList<lineaOrdenVenta>();

            producto producto = productoWS.obtenerProducto(int.Parse(txtIDProducto.Text));

            lineaOrdenVenta linea = new lineaOrdenVenta {
                producto = producto,
                cantidad = int.Parse(txtCantidadUnidades.Text),
                subTotal = producto.precio * int.Parse(txtCantidadUnidades.Text),
                activo = true
            };

            lineas.Add(linea);
            Session["LineasOrdenVenta"] = lineas;

            double subtotal = lineas.Sum(l => l.subTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            txtSubtotal.Text = subtotal.ToString("N2");
            txtIGV.Text = igv.ToString("N2");
            txtTotal.Text = total.ToString("N2");

            gvDetallesOrden.DataSource = lineas.Select(l => new {
                IdProducto = l.producto.id,
                NombreProducto = l.producto.nombre,
                PrecioUnitario = l.producto.precio,
                Cantidad = l.cantidad,
                Subtotal = l.subTotal
            });
            gvDetallesOrden.DataBind();

            txtIDProducto.Text = string.Empty;
            txtNombreProducto.Text = string.Empty;
            txtPrecioUnitProducto.Text = string.Empty;
            txtCantidadUnidades.Text = string.Empty;
        }

        protected void btnEliminarItem_Click(object sender, EventArgs e) {
            var btn = (LinkButton)sender;
            int idProducto = int.Parse(btn.CommandArgument);

            BindingList<lineaOrdenVenta> lineas = Session["LineasOrdenVenta"] as BindingList<lineaOrdenVenta>;
            if (lineas == null) return;

            lineas = new BindingList<lineaOrdenVenta>(
                lineas.Where(l => l.producto == null || l.producto.id != idProducto).ToList()
            );

            Session["LineasOrdenVenta"] = lineas;

            gvDetallesOrden.DataSource = lineas.Select(l => new
            {
                IdProducto = l.producto.id,
                NombreProducto = l.producto.nombre,
                PrecioUnitario = l.producto.precio,
                Cantidad = l.cantidad,
                Subtotal = l.subTotal
            });
            gvDetallesOrden.DataBind();

            double subtotal = lineas.Sum(l => l.subTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            txtSubtotal.Text = subtotal.ToString("N2");
            txtIGV.Text = igv.ToString("N2");
            txtTotal.Text = total.ToString("N2");
        }

        protected void btnGuardarOrden_Click(object sender, EventArgs e) {
            BindingList<lineaOrdenVenta> lineas = Session["LineasOrdenVenta"] as BindingList<lineaOrdenVenta>;
            if (lineas == null || lineas.Count == 0)
                return;

            double subtotal = lineas.Sum(l => l.subTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            ordenVenta ordenVenta = new ordenVenta {
                cliente = clienteWS.buscarClientePorDni(txtDNICliente.Text),
                activo = true,
                lineas = lineas.ToArray(),
                total = total
            };

            ordenVentaWS.guardarOrdenVenta(ordenVenta, estado.Nuevo);

            Session.Remove("LineasOrdenVenta");
        }
    }
}