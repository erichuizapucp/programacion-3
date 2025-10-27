using PUCP.SoftProg.Web.SoftProgWS;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class GestionarEmpleados : System.Web.UI.Page {
        private AreaWSClient areaWS;
        private EmpleadoWSClient empleadoWS;
        private empleado empleado;
        private estado estado;

        protected void Page_Init(object sender, EventArgs e) {
            //Se cargan las areas
            areaWS = new AreaWSClient();
            ddlAreas.DataSource = new BindingList<area>(areaWS.listarAreas());
            ddlAreas.DataTextField = "nombre";
            ddlAreas.DataValueField = "id";
            ddlAreas.DataBind();

            //Verificamos si es una acción de modificación
            string accion = Request.QueryString["accion"];
            if (accion == null) {
                lblTitulo.Text = "Registrar Empleado";
                empleado = new empleado();
                estado = estado.Nuevo;
                if (!IsPostBack) {
                    Session["empleado"] = null;
                }
            }
            else if (accion == "modificar" && Session["empleado"] != null) {
                lblTitulo.Text = "Modificar Empleado";
                estado = estado.Modificado;
                empleado = (empleado)Session["empleado"];
                hdnIdEmpleado.Value = empleado.id.ToString();
                txtDNIEmpleado.Text = empleado.dni;
                ddlCargo.SelectedValue = empleado.cargo.ToString();
                txtApellidoPaterno.Text = empleado.apellidoPaterno;
                txtNombre.Text = empleado.nombre;

                if (empleado.genero == genero.MASCULINO) {
                    rbMasculino.Checked = true;
                }
                else {
                    rbFemenino.Checked = true;
                }

                txtSueldo.Text = empleado.sueldo.ToString("N2");
                ddlAreas.SelectedValue = empleado.area.id.ToString();
                dtpFechaNacimiento.Value = empleado.fechaNacimiento.ToString("yyyy-MM-dd");
            }
        }

        protected void GuardarEmpleado(object sender, EventArgs e) {
            empleadoWS = new EmpleadoWSClient();

            //Asignamos los valores
            if (estado == estado.Modificado) {
                empleado.id = int.Parse(hdnIdEmpleado.Value);
            }

            empleado.dni = txtDNIEmpleado.Text;
            empleado.nombre = txtNombre.Text;
            empleado.apellidoPaterno = txtApellidoPaterno.Text;
            empleado.area = new area {
                id = int.Parse(ddlAreas.SelectedValue)
            };

            if (rbMasculino.Checked) {
                empleado.genero = genero.MASCULINO;
            }
            else {
                empleado.genero = genero.FEMENINO;
            }

            empleado.fechaNacimiento = DateTime.Parse(dtpFechaNacimiento.Value);
            empleado.fechaNacimientoSpecified = true;
            empleado.cargo = (cargo)Enum.Parse(typeof(cargo), ddlCargo.SelectedItem.Value);
            empleado.sueldo = double.Parse(txtSueldo.Text);

            empleadoWS.guardarEmpleado(empleado, estado);
            Response.Redirect("ListarEmpleados.aspx");
        }
    }
}