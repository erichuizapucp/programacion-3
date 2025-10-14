using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.Clientes;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class RegistrarCliente : System.Web.UI.Page {
        private IClienteBO clienteBO;

        public RegistrarCliente() { 
            clienteBO = new ClienteBOImpl();
        }

        protected void Page_Load(object sender, EventArgs e) {
            if (!IsPostBack) {
                ActualizarProgreso();
            }
        }

        protected void WizardRegistro_NextButtonClick(object sender, WizardNavigationEventArgs e) {
            if (e.CurrentStepIndex == 0) {
                string password = Request.Form[txtPassword.UniqueID];
                Session["PasswordTemporal"] = password;

                if (string.IsNullOrWhiteSpace(password)) {
                    e.Cancel = true;
                    return;
                }
            }
        }

        protected void WizardRegistro_ActiveStepChanged(object sender, EventArgs e) {
            ActualizarProgreso();
        }

        private void ActualizarProgreso() {
            int pasoActualIndex = WizardRegistro.ActiveStepIndex;
            int totalPasos = WizardRegistro.WizardSteps.Count;

            string tituloPaso = WizardRegistro.ActiveStep.Title;
            if (WizardRegistro.ActiveStep.StepType == WizardStepType.Complete) {
                lblPaso.Text = "Registro Completo";
            }
            else {
                lblPaso.Text = $"Paso {pasoActualIndex + 1} de {totalPasos}: {tituloPaso}";
            }

            if (totalPasos > 0) {
                int porcentaje;
                if (WizardRegistro.ActiveStep.StepType == WizardStepType.Complete) {
                    porcentaje = 100;
                }
                else {
                    porcentaje = (int)(((double)(pasoActualIndex + 1) / totalPasos) * 100);
                }

                progressBar.Style["width"] = $"{porcentaje}%";
                progressBar.Attributes["aria-valuenow"] = (pasoActualIndex + 1).ToString();
                progressBar.Attributes["aria-valuemax"] = totalPasos.ToString();
            }
        }

        protected void WizardRegistro_FinishButtonClick(object sender, WizardNavigationEventArgs e) {
            if (!Page.IsValid) {
                return;
            }

            if (Session["PasswordTemporal"] == null) { 
                e.Cancel = true;
                return;
            }

            Cliente cliente = new Cliente() {
                Dni = txtDni.Text,
                Nombre = txtNombre.Text,
                ApellidoPaterno = txtApellidoPaterno.Text,
                Genero = (Genero)Enum.Parse(typeof(Genero), rblGenero.SelectedItem.Value),
                FechaNacimiento = DateTime.Parse(txtFechaNacimiento.Text),
                LineaCredito = double.Parse(txtLineaCredito.Text),
                Categoria = (CategoriaCliente)Enum.Parse(typeof(CategoriaCliente), rblCategoria.SelectedItem.Value),
                CuentaUsuario = new CuentaUsuario() {
                    UserName = txtUser.Text,
                    Password = Session["PasswordTemporal"].ToString(),
                },
                IsActive = true,
            };

            clienteBO.Registrar(cliente);
            Session["PasswordTemporal"] = null;

            WizardRegistro.Visible = false;
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