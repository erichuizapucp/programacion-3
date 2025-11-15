using System;
using System.Web.UI;

namespace PUCP.SoftProg.Web {
    public partial class OlvidoContrasena : System.Web.UI.Page {
        protected void Page_Load(object sender, EventArgs e) {

        }

        protected void btnEnviar_Click(object sender, EventArgs e) {
            if (Page.IsValid) {
                string correo = txtCorreo.Text.Trim();

                // TODO: Aquí deberías generar y enviar la nueva contraseña al correo

                pnlFormulario.Visible = false;
                pnlMensaje.Visible = true;

                string script = @"
                    setTimeout(function() {
                        window.location.href = 'Login.aspx';
                    }, 10000);
                ";
                ClientScript.RegisterStartupScript(this.GetType(), "redirigir", script, true);
            }
        }
    }
}