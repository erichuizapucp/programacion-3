using Microsoft.Ajax.Utilities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Runtime.Remoting;
using System.ServiceModel.Security;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.SoftProg.Web.SoftProgWS;

namespace PUCP.SoftProg.Web {
    public partial class PerfilCliente : System.Web.UI.Page {
        private ClienteWSClient clientWS;

        public PerfilCliente() { 
            this.clientWS = new ClienteWSClient();
        }

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                pnlMensaje.Visible = false;

                string cuenta = Page.User.Identity.Name;
                cliente cliente = 
                    this.clientWS.buscarClientePorCuenta(cuenta);

                lblIdRegistro.Text = cliente.id.ToString();
                txtDNI.Text = cliente.dni;
                txtNombre.Text = cliente.nombre;
                txtApellidoPaterno.Text = cliente.apellidoPaterno;
                rblGenero.SelectedValue = cliente.genero.ToString().ToUpper();
                txtFechaNacimiento.Text = cliente.fechaNacimiento.ToString("yyyy-MM-dd");
                txtLineaCredito.Text = cliente.lineaCredito.ToString("N2");
                ddlCategoria.SelectedValue = cliente.categoria.ToString().ToUpper();
                txtCuentaUsuario.Text = cuenta;

                Session["Cliente"] = cliente;
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e) {
            if (!IsValid) {
                return;
            }

            cliente cliente = (cliente)Session["Cliente"];
            cliente.dni = txtDNI.Text;
            cliente.nombre = txtNombre.Text;
            cliente.apellidoPaterno = txtApellidoPaterno.Text;
            cliente.genero = (genero)Enum.Parse(typeof(genero), rblGenero.SelectedValue, true);
            cliente.fechaNacimiento = DateTime.Parse(txtFechaNacimiento.Text);
            cliente.fechaNacimientoSpecified = true;

            cliente.lineaCredito = double.Parse(txtLineaCredito.Text);
            cliente.categoria = (categoriaCliente)Enum.Parse(typeof(categoriaCliente), ddlCategoria.SelectedValue, true);

            this.clientWS.guardarCliente(cliente, estado.Modificado);

            pnlMensaje.Visible = true;
        }
    }
}