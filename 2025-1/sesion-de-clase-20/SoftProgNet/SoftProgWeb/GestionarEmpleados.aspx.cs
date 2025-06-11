using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.Edu.Pe.SoftProg.Web.SoftProgWS;

namespace PUCP.Edu.Pe.SoftProg.Web {
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
                estado = estado.Modificar;
                empleado = (empleado)Session["empleado"];
                txtDNIEmpleado.Text = empleado.dni;
                txtCargo.Text = empleado.cargo;
                txtApellidoPaterno.Text = empleado.apellidoPaterno;
                txtNombre.Text = empleado.nombre;
                if (empleado.genero.Equals('M')) rbMasculino.Checked = true;
                else rbFemenino.Checked = true;
                txtSueldo.Text = empleado.sueldo.ToString("N2");
                ddlAreas.SelectedValue = empleado.area.id.ToString();
                dtpFechaNacimiento.Value = empleado.fechaNacimiento.ToString("yyyy-MM-dd");
            }
        }

        protected void guardar_empleado(object sender, EventArgs e) {
            empleadoWS = new EmpleadoWSClient();

            //Asignamos los valores
            empleado.dni = txtDNIEmpleado.Text;
            empleado.nombre = txtNombre.Text;
            empleado.apellidoPaterno = txtApellidoPaterno.Text;
            empleado.area = new area();
            empleado.area.id = Int32.Parse(ddlAreas.SelectedValue);
            if (rbMasculino.Checked)
                empleado.genero = 'M';
            else
                empleado.genero = 'F';
            empleado.fechaNacimiento = DateTime.Parse(dtpFechaNacimiento.Value);
            empleado.fechaNacimientoSpecified = true;
            empleado.cargo = txtCargo.Text;
            empleado.sueldo = Double.Parse(txtSueldo.Text);

            empleadoWS.guardarEmpleado(empleado, estado);

            Response.Redirect("ListarEmpleados.aspx");
        }
    }
}