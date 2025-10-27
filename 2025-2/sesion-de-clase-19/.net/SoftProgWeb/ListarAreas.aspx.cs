using System;
using System.ComponentModel;
using System.Linq;
using System.Web.UI.WebControls;
using PUCP.SoftProg.Web.SoftProgWS;

namespace PUCP.SoftProg.Web
{
    public partial class ListarAreas : System.Web.UI.Page
    {
        private readonly AreaWSClient clientWS;
        private BindingList<area> areas;

        public ListarAreas() {
            this.clientWS = new AreaWSClient();
        }

        protected void Page_Init(object sender, EventArgs e) {
            areas = new BindingList<area>(clientWS.listarAreas());
            
            gvAreas.DataSource = areas;
            gvAreas.DataBind();
        }

        protected void gvAreas_PageIndexChanging(object sender, 
            GridViewPageEventArgs e) {

            gvAreas.PageIndex = e.NewPageIndex;
            gvAreas.DataBind();
        }

        protected void lbRegistrar_Click(object sender, EventArgs e) {
            Response.Redirect("GestionarAreas.aspx");
        }

        protected void lbModificar_Click(object sender, EventArgs e) {
            int idArea = int.Parse(((LinkButton)sender).CommandArgument);
            area area = areas.SingleOrDefault(x => x.id == idArea);
            Session["area"] = area;
            Response.Redirect("GestionarAreas.aspx?accion=modificar");
        }

        protected void lbEliminar_Click(object sender, EventArgs e) {
            int idArea = Int32.Parse(((LinkButton)sender).CommandArgument);
            clientWS.eliminarArea(idArea);
            Response.Redirect("ListarAreas.aspx");
        }
    }
}