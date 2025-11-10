using System;
using System.ComponentModel;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.SoftProg.Web.SoftProgWS;

namespace PUCP.SoftProg.Web {
    public partial class ListarOrdenesVenta : Page {
        private readonly OrdenVentaWSClient ordenVentaWS = new OrdenVentaWSClient();

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                CargarOrdenes();
            }
        }

        private void CargarOrdenes() {
            string cuenta = Page.User.Identity.Name;

            BindingList<ordenVenta> ordenes = new BindingList<ordenVenta>(ordenVentaWS.listarOrdenesVentaPorCuenta(cuenta));
            gvOrdenes.DataSource = ordenes;
            gvOrdenes.DataBind();
        }

        protected void gvOrdenes_PageIndexChanging(object sender, GridViewPageEventArgs e) {
            gvOrdenes.PageIndex = e.NewPageIndex;
            CargarOrdenes();
        }
    }
}