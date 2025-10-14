using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.Almacen;
using PUCP.SoftProg.Modelo.Clientes;
using PUCP.SoftProg.Modelo.Ventas;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;
using System.ComponentModel;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class GestionarOrdenesVenta : Page {
        private readonly IClienteBO clienteBO;
        private readonly IProductoBO productoBO;
        private readonly IOrdenVentaBO ordenVentaBO;
        private BindingList<Producto> productos;

        protected BindingList<LineaOrdenVenta> LineasOrden {
            get {
                if (Session["LineasOrden"] == null)
                    Session["LineasOrden"] = new BindingList<LineaOrdenVenta>();
                return (BindingList<LineaOrdenVenta>)Session["LineasOrden"];
            }
            set {
                Session["LineasOrden"] = value;
            }
        }

        public GestionarOrdenesVenta() {
            clienteBO = new ClienteBOImpl();
            productoBO = new ProductoBOImpl();
            ordenVentaBO = new OrdenVentaBOImpl();
        }

        protected void Page_Load(object sender, EventArgs e){
            if (!IsPostBack) {
                string cuenta = Page.User.Identity.Name;
                Cliente cliente = clienteBO.BuscarPorCuenta(cuenta);

                hdnIdCliente.Value = cliente.Id.ToString();
                txtDNICliente.Text = cliente.Dni;
                txtNombreCliente.Text = $"{cliente.Nombre} {cliente.ApellidoPaterno}";
            }
            
            productos = new BindingList<Producto>(productoBO.Listar());
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
            Producto producto = productos.SingleOrDefault(x => x.Id == idProducto);

            txtIDProducto.Text = producto.Id.ToString();
            txtNombreProducto.Text = producto.Nombre + " " + producto.UnidadMedida;
            txtPrecioUnitProducto.Text = producto.Precio.ToString("N2");
        }

        protected void lbAgregarLOV_Click(object sender, EventArgs e) {
            if (!(Session["LineasOrdenVenta"] is BindingList<LineaOrdenVenta> lineas))
                lineas = new BindingList<LineaOrdenVenta>();

            Producto producto = productoBO.Obtener(int.Parse(txtIDProducto.Text));

            LineaOrdenVenta linea = new LineaOrdenVenta {
                Producto = producto,
                Cantidad = int.Parse(txtCantidadUnidades.Text),
                SubTotal = producto.Precio * int.Parse(txtCantidadUnidades.Text),
                IsActive = true
            };

            lineas.Add(linea);
            Session["LineasOrdenVenta"] = lineas;

            double subtotal = lineas.Sum(l => l.SubTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            txtSubtotal.Text = subtotal.ToString("N2");
            txtIGV.Text = igv.ToString("N2");
            txtTotal.Text = total.ToString("N2");

            gvDetallesOrden.DataSource = lineas.Select(l => new {
                IdProducto = l.Producto.Id,
                NombreProducto = l.Producto.Nombre,
                PrecioUnitario = l.Producto.Precio,
                Cantidad = l.Cantidad,
                Subtotal = l.SubTotal
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

            BindingList<LineaOrdenVenta> lineas = Session["LineasOrdenVenta"] as BindingList<LineaOrdenVenta>;
            if (lineas == null) return;

            lineas = new BindingList<LineaOrdenVenta>(
                lineas.Where(l => l.Producto == null || l.Producto.Id != idProducto).ToList()
            );

            Session["LineasOrdenVenta"] = lineas;

            gvDetallesOrden.DataSource = lineas.Select(l => new
            {
                IdProducto = l.Producto.Id,
                NombreProducto = l.Producto.Nombre,
                PrecioUnitario = l.Producto.Precio,
                Cantidad = l.Cantidad,
                Subtotal = l.SubTotal
            });
            gvDetallesOrden.DataBind();

            double subtotal = lineas.Sum(l => l.SubTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            txtSubtotal.Text = subtotal.ToString("N2");
            txtIGV.Text = igv.ToString("N2");
            txtTotal.Text = total.ToString("N2");
        }

        protected void btnGuardarOrden_Click(object sender, EventArgs e) {
            BindingList<LineaOrdenVenta> lineas = Session["LineasOrdenVenta"] as BindingList<LineaOrdenVenta>;
            if (lineas == null || lineas.Count == 0)
                return;

            double subtotal = lineas.Sum(l => l.SubTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            OrdenVenta ordenVenta = new OrdenVenta {
                FechaHora = DateTime.Now,
                Cliente = clienteBO.BuscarPorDni(txtDNICliente.Text),
                IsActive = true,
                LineasOrdenVenta = lineas.ToList(),
                Total = total
            };

            ordenVentaBO.Guardar(ordenVenta, Estado.Nuevo);

            Session.Remove("LineasOrdenVenta");
        }
    }
}