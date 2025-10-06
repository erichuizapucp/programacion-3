using Microsoft.Ajax.Utilities;
using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.Clientes;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Runtime.Remoting;
using System.ServiceModel.Security;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class PerfilCliente : System.Web.UI.Page {
        private IClienteBO clienteBO;

        public PerfilCliente() { 
            this.clienteBO = new ClienteBOImpl();
        }

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                pnlMensaje.Visible = false;

                string cuenta = Page.User.Identity.Name;
                Cliente cliente = this.clienteBO.BuscarPorCuenta(cuenta);

                lblIdRegistro.Text = cliente.Id.ToString();
                txtDNI.Text = cliente.Dni;
                txtNombre.Text = cliente.Nombre;
                txtApellidoPaterno.Text = cliente.ApellidoPaterno;
                rblGenero.SelectedValue = cliente.Genero.ToString().ToUpper();
                txtFechaNacimiento.Text = cliente.FechaNacimiento.ToString("yyyy-MM-dd");
                txtLineaCredito.Text = cliente.LineaCredito.ToString("N2");
                ddlCategoria.SelectedValue = cliente.Categoria.ToString().ToUpper();
                txtCuentaUsuario.Text = cuenta;

                Session["Cliente"] = cliente;
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e) {
            if (!IsValid) {
                return;
            }

            Cliente cliente = (Cliente)Session["Cliente"];
            cliente.Dni = txtDNI.Text;
            cliente.Nombre = txtNombre.Text;
            cliente.ApellidoPaterno = txtApellidoPaterno.Text;
            cliente.Genero = (Genero)Enum.Parse(typeof(Genero), rblGenero.SelectedValue, true);
            cliente.FechaNacimiento = DateTime.Parse(txtFechaNacimiento.Text);
            cliente.LineaCredito = double.Parse(txtLineaCredito.Text);
            cliente.Categoria = (CategoriaCliente)Enum.Parse(typeof(CategoriaCliente), ddlCategoria.SelectedValue, true);

            this.clienteBO.Guardar(cliente, Estado.Modificado);

            pnlMensaje.Visible = true;
        }
    }
}