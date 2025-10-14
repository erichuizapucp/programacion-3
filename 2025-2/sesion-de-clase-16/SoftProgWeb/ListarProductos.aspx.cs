using PUCP.SoftProg.Modelo.Almacen;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;
using System.ComponentModel;
using System.Linq;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class ListarProductos : Page {
        private readonly IProductoBO productoBO;
        private BindingList<Producto> productos;

        public ListarProductos() {
            this.productoBO = new ProductoBOImpl();
        }

        protected void Page_Load(object sender, EventArgs e) {
            productos = new BindingList<Producto>(productoBO.Listar());

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
            Producto producto = productos.SingleOrDefault(x => x.Id == idProducto);
            Session["producto"] = producto;
            Response.Redirect("GestionarProductos.aspx?accion=modificar");
        }

        protected void lbEliminar_Click(object sender, EventArgs e) {
            int idProducto = int.Parse(((LinkButton)sender).CommandArgument);
            productoBO.Eliminar(idProducto);
            Response.Redirect("ListarProductos.aspx");
        }
    }
}