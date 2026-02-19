using System;
using System.Web.Security;
using System.Web.UI;

namespace PUCP.SoftProg.Web {
    public partial class Logout : Page {
        protected void Page_Load(object sender, EventArgs e) {
            FormsAuthentication.SignOut();
            Session.Clear();
            Response.Redirect("Login.aspx");
        }
    }
}