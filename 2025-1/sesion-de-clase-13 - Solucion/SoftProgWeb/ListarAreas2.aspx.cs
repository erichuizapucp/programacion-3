using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Negocio;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;

namespace PUCP.Edu.Pe.SoftProg.Web {
    public partial class ListarAreas2 : System.Web.UI.Page {
        private readonly IAreaBO areaBO;
        private BindingList<Area> areas;

        public ListarAreas2()
        {
            this.areaBO = new AreaBOImpl();
        }

        protected void Page_Init(object sender, EventArgs e) {
            areas = new BindingList<Area>(areaBO.Listar());

            gvAreas.DataSource = areas;
            gvAreas.DataBind();
        }

        protected void lbRegistrar_Click(object sender, EventArgs e) {
            Response.Redirect("GestionarAreas.aspx");
        }

        protected void gvAreas_OnPageIndexChanging(object sender, GridViewPageEventArgs e) {
            gvAreas.PageIndex = e.NewPageIndex;
            gvAreas.DataBind();
        }

        protected void btnModificar_Click(object sender, EventArgs e) {
            int idArea = Int32.Parse(((LinkButton)sender).CommandArgument);
            Area area = areas.SingleOrDefault(x => x.Id == idArea);
            Session["area"] = area;
            Response.Redirect("GestionarAreas.aspx?accion=modificar");
        }

        protected void btnEliminar_Click(object sender, EventArgs e) {
            int idArea = Int32.Parse(((LinkButton)sender).CommandArgument);
            areaBO.Eliminar(idArea);
            Response.Redirect("ListarAreas2.aspx");
        }
    }
}