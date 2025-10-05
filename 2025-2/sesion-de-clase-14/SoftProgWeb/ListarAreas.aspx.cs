using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;
using System.ComponentModel;
using System.Linq;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web
{
    public partial class ListarAreas : System.Web.UI.Page
    {
        private readonly IAreaBO areaBO;
        private BindingList<Area> areas;

        public ListarAreas() {
            this.areaBO = new AreaBOImpl();
        }

        protected void Page_Init(object sender, EventArgs e) {
            areas = new BindingList<Area>(areaBO.Listar());
            
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
            Area area = areas.SingleOrDefault(x => x.Id == idArea);
            Session["area"] = area;
            Response.Redirect("GestionarAreas.aspx?accion=modificar");
        }

        protected void lbEliminar_Click(object sender, EventArgs e) {
            int idArea = Int32.Parse(((LinkButton)sender).CommandArgument);
            areaBO.Eliminar(idArea);
            Response.Redirect("ListarAreas.aspx");
        }
    }
}