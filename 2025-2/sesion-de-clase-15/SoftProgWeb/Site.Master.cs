using PUCP.SoftProg.Modelo;
using System;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;

namespace PUCP.SoftProg.Web {
    public partial class SiteMaster : MasterPage {
        protected HtmlAnchor lnkPerfilCliente;

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                this.DataBind();
            }

            if (Page.User.Identity.IsAuthenticated) {
                string cuenta = Page.User.Identity.Name;
                lblCuenta.Text = cuenta;

                TipoUsuario tipoUsuario = (TipoUsuario)Enum.Parse(typeof(TipoUsuario), Session["TipoUsuario"].ToString());
                if (tipoUsuario == TipoUsuario.EMPLEADO) {
                    lnkPerfilCliente.Visible = false;
                }   
            }
            else {
                FormsAuthentication.RedirectToLoginPage();
            }
        }
    }
}