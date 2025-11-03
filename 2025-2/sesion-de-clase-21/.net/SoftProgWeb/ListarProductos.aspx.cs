using System;
using System.ComponentModel;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.SoftProg.Web.SoftProgWS;

namespace PUCP.SoftProg.Web {
    public partial class ListarProductos : Page {
        private readonly ProductoWSClient productoWS;
        private BindingList<producto> productos;

        public ListarProductos() {
            this.productoWS = new ProductoWSClient();
        }

        protected void Page_Load(object sender, EventArgs e) {
            productos = new BindingList<producto>(productoWS.listarProductos());

            gvProductos.DataSource = productos;
            gvProductos.DataBind();
        }

        protected void lbRegistrar_Click(object sender, EventArgs e) {
            Response.Redirect("GestionarProductos.aspx");
        }

        protected void gvProductos_PageIndexChanging(object sender,
            GridViewPageEventArgs e) {

            gvProductos.PageIndex = e.NewPageIndex;
            gvProductos.DataBind();
        }

        protected void lbModificar_Click(object sender, EventArgs e) {
            int idProducto = int.Parse(((LinkButton)sender).CommandArgument);
            producto producto = productos.SingleOrDefault(x => x.id == idProducto);
            Session["producto"] = producto;
            Response.Redirect("GestionarProductos.aspx?accion=modificar");
        }

        protected void lbEliminar_Click(object sender, EventArgs e) {
            int idProducto = int.Parse(((LinkButton)sender).CommandArgument);
            productoWS.eliminarProducto(idProducto);
            Response.Redirect("ListarProductos.aspx");
        }
    }
}