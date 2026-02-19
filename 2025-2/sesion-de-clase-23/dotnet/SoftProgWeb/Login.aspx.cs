using System;
using PUCP.SoftProg.Web.SoftProgWS;

namespace PUCP.SoftProg.Web {
    public partial class Login : System.Web.UI.Page {
        private CuentaUsuarioWSClient clientWS;

        public Login() { 
            this.clientWS = new CuentaUsuarioWSClient();
        }

        protected void Page_Load(object sender, EventArgs e) {
        }

        protected void btnLogin_Click(object sender, EventArgs e) {
            if (!IsValid) {
                return;
            }

            string cuenta = txtCuenta.Text.Trim();
            string password = txtPassword.Text;

            if (!this.clientWS.login(cuenta, password)) {
                cvLoginError.IsValid = false;
                cvLoginError.ErrorMessage = "La cuenta o contraseña es incorrecta.";
                
                return;
            }

            TipoUsuario tipoUsuario = (TipoUsuario)Enum.Parse(typeof(TipoUsuario), rblTipoUsuario.SelectedValue);
            Session["TipoUsuario"] = tipoUsuario.ToString();

            Session["CuentaUsuario"] = cuenta;
            System.Web.Security.FormsAuthentication.RedirectFromLoginPage(cuenta, false);
        }
    }
}