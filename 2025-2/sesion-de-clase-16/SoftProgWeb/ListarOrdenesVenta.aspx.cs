using PUCP.SoftProg.Modelo.Ventas;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class ListarOrdenesVenta : Page {
        private readonly IOrdenVentaBO ordenVentaBO = new OrdenVentaBOImpl();

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                CargarOrdenes();
            }
        }

        private void CargarOrdenes() {
            string cuenta = Page.User.Identity.Name;

            BindingList<OrdenVenta> ordenes = new BindingList<OrdenVenta>(ordenVentaBO.ListarPorCuenta(cuenta));
            gvOrdenes.DataSource = ordenes;
            gvOrdenes.DataBind();
        }

        protected void gvOrdenes_PageIndexChanging(object sender, GridViewPageEventArgs e) {
            gvOrdenes.PageIndex = e.NewPageIndex;
            CargarOrdenes();
        }
    }
}