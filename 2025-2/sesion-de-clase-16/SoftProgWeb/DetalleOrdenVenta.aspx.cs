using PUCP.SoftProg.Modelo.Ventas;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;
using System.Linq;
using System.Web.UI;

namespace PUCP.SoftProg.Web {
    public partial class DetalleOrdenVenta : Page {
        private readonly IOrdenVentaBO ordenVentaBO = new OrdenVentaBOImpl();

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                string idParam = Request.QueryString["id"];
                if (int.TryParse(idParam, out int idOrden)) {
                    CargarDetalleOrden(idOrden);
                }
            }
        }

        private void CargarDetalleOrden(int idOrden) {
            OrdenVenta orden = ordenVentaBO.Obtener(idOrden);
            if (orden == null) return;

            txtIdOrden.Text = orden.Id.ToString();
            txtCliente.Text = $"{orden.Cliente.Nombre} {orden.Cliente.ApellidoPaterno}";
            txtDniCliente.Text = orden.Cliente.Dni;

            gvLineasOrden.DataSource = orden.LineasOrdenVenta;
            gvLineasOrden.DataBind();

            double subtotal = orden.LineasOrdenVenta.Sum(l => l.SubTotal);
            double igv = subtotal * 0.18;
            double total = subtotal + igv;

            txtSubtotal.Text = subtotal.ToString("N2");
            txtIGV.Text = igv.ToString("N2");
            txtTotal.Text = total.ToString("N2");
        }
    }
}