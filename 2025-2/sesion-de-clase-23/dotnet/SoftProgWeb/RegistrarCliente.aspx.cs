using System;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.SoftProg.Web.SoftProgWS;

namespace PUCP.SoftProg.Web {
    public partial class RegistrarCliente : System.Web.UI.Page {
        private ClienteWSClient clienteWS;

        public RegistrarCliente() {
            clienteWS = new ClienteWSClient();
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

            cliente cliente = new cliente() {
                dni = txtDni.Text,
                nombre = txtNombre.Text,
                apellidoPaterno = txtApellidoPaterno.Text,
                genero = (genero)Enum.Parse(typeof(genero), rblGenero.SelectedItem.Value),
                fechaNacimiento = DateTime.Parse(txtFechaNacimiento.Text),
                lineaCredito = double.Parse(txtLineaCredito.Text),
                categoria = (categoriaCliente)Enum.Parse(typeof(categoriaCliente), rblCategoria.SelectedItem.Value),
                cuentaUsuario = new cuentaUsuario() {
                    userName = txtUser.Text,
                    password = Session["PasswordTemporal"].ToString(),
                },
                activo = true,
            };
            cliente.fechaNacimientoSpecified = true;

            //clienteWS.regis(cliente);
            // TODO: Implementar en java
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