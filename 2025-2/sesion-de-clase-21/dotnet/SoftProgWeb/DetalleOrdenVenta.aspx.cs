using System;
using System.Linq;
using System.Web.UI;
using PUCP.SoftProg.Web.SoftProgWS;

namespace PUCP.SoftProg.Web {
    public partial class DetalleOrdenVenta : Page {
        private readonly OrdenVentaWSClient ordenVentaWS 
            = new OrdenVentaWSClient();

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                string idParam = Request.QueryString["id"];
                if (int.TryParse(idParam, out int idOrden)) {
                    CargarDetalleOrden(idOrden);
                }
            }
        }

        private void CargarDetalleOrden(int idOrden) {
            ordenVenta orden = ordenVentaWS.obtenerOrdenVenta(idOrden);
            if (orden == null) return;

            txtIdOrden.Text = orden.id.ToString();
            txtCliente.Text = $"{orden.cliente.nombre} {orden.cliente.apellidoPaterno}";
            txtDniCliente.Text = orden.cliente.dni;

            gvLineasOrden.DataSource = orden.lineas;
            gvLineasOrden.DataBind();

            double subtotal = orden.lineas.Sum(l => l.subTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            txtSubtotal.Text = subtotal.ToString("N2");
            txtIGV.Text = igv.ToString("N2");
            txtTotal.Text = total.ToString("N2");
        }
    }
}