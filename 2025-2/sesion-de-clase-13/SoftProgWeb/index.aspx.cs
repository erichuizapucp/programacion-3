using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace SoftProgWeb {
    public partial class WebForm1 : System.Web.UI.Page {
        private BindingList<Usuario> usuarios;

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                usuarios = (BindingList<Usuario>)Session["usuarios"];
                gvUsuarios.DataSource = usuarios;
                gvUsuarios.DataBind();
            }
            else {
                if (Session["usuarios"] == null) {
                    usuarios = new BindingList<Usuario>();
                    Session["usuarios"] = usuarios;
                }
            }
        }

        protected void btnSubmit_Click(object sender, EventArgs e) {
            string email = txtEmail.Text;
            string password = txtPassword.Text;
            bool isChecked = chkCheck.Checked;

            usuarios = (BindingList<Usuario>)Session["usuarios"];
            usuarios.Add(new Usuario {
                Email = email,
                Password = password,
                Check = isChecked
            });

            gvUsuarios.DataSource = usuarios;
            gvUsuarios.DataBind();
        }
    }
}