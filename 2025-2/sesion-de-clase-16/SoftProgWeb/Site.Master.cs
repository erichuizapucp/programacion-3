using PUCP.SoftProg.Modelo;
using System;
using System.Web.Security;
using System.Web.UI;

namespace PUCP.SoftProg.Web {
    public partial class SiteMaster : MasterPage {
        protected void Page_Load(object sender, EventArgs e) {
            if (Page.User.Identity.IsAuthenticated) {
                string cuenta = Page.User.Identity.Name;
                lblCuenta.Text = cuenta;

                TipoUsuario tipoUsuario = (TipoUsuario)Enum.Parse(typeof(TipoUsuario), Session["TipoUsuario"].ToString());
                if (tipoUsuario == TipoUsuario.EMPLEADO) {
                    lnkPerfilCliente.Visible = false;
                    lnkGestionarAreas.Visible = true;
                    lnkListarAreas.Visible = true;
                    lnkGestionarProductos.Visible = true;
                    lnkListarProductos.Visible = true;
                    lnkGestionarEmpleados.Visible = true;
                    lnkListarEmpleados.Visible = true;
                    lnkGestionarOrdenesVenta.Visible = false;
                    lnkListarOrdenesVenta.Visible = false;
                }
                else {
                    lnkPerfilCliente.Visible = true;
                    lnkGestionarAreas.Visible = false;
                    lnkListarAreas.Visible = false;
                    lnkGestionarProductos.Visible = false;
                    lnkListarProductos.Visible = false;
                    lnkGestionarEmpleados.Visible = false;
                    lnkListarEmpleados.Visible = false;
                    lnkGestionarOrdenesVenta.Visible = true;
                    lnkListarOrdenesVenta.Visible = true;
                }
            }
            else {
                FormsAuthentication.RedirectToLoginPage();
            }
        }
    }
}