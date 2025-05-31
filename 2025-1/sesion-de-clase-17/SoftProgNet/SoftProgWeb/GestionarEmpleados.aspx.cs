using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Negocio;
using PUCP.Edu.Pe.SoftProg.Negocio.BOImpl;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Web {
    public partial class GestionarEmpleados : System.Web.UI.Page {
        private AreaBOImpl areaBO;
        private EmpleadoBOImpl empleadoBO;
        private Empleado empleado;
        private Estado estado;

        protected void Page_Init(object sender, EventArgs e) {
            //Se cargan las areas
            areaBO = new AreaBOImpl();
            ddlAreas.DataSource = new BindingList<Area>(areaBO.Listar());
            ddlAreas.DataTextField = "Nombre";
            ddlAreas.DataValueField = "Id";
            ddlAreas.DataBind();

            //Verificamos si es una acción de modificación
            string accion = Request.QueryString["accion"];
            if (accion == null) {
                lblTitulo.Text = "Registrar Empleado";
                empleado = new Empleado();
                estado = Estado.Nuevo;
                if (!IsPostBack) {
                    Session["empleado"] = null;
                }
            }
            else if (accion == "modificar" && Session["empleado"] != null) {
                lblTitulo.Text = "Modificar Empleado";
                estado = Estado.Modificar;
                empleado = (Empleado)Session["empleado"];
                txtDNIEmpleado.Text = empleado.Dni;
                txtCargo.Text = empleado.Cargo;
                txtApellidoPaterno.Text = empleado.ApellidoPaterno;
                txtNombre.Text = empleado.Nombre;
                if (empleado.Genero.Equals('M')) rbMasculino.Checked = true;
                else rbFemenino.Checked = true;
                txtSueldo.Text = empleado.Sueldo.ToString("N2");
                ddlAreas.SelectedValue = empleado.Area.Id.ToString();
                dtpFechaNacimiento.Value = empleado.FechaNacimiento.ToString("yyyy-MM-dd");
            }
        }

        protected void guardar_empleado(object sender, EventArgs e) {
            empleadoBO = new EmpleadoBOImpl();

            //Asignamos los valores
            empleado.Dni = txtDNIEmpleado.Text;
            empleado.Nombre = txtNombre.Text;
            empleado.ApellidoPaterno = txtApellidoPaterno.Text;
            empleado.Area = new Area();
            empleado.Area.Id = Int32.Parse(ddlAreas.SelectedValue);
            if (rbMasculino.Checked)
                empleado.Genero = 'M';
            else
                empleado.Genero = 'F';
            empleado.FechaNacimiento =
                DateTime.Parse(dtpFechaNacimiento.Value);
            empleado.Cargo = txtCargo.Text;
            empleado.Sueldo = Double.Parse(txtSueldo.Text);

            empleadoBO.Guardar(empleado, estado);

            Response.Redirect("ListarEmpleados.aspx");
        }
    }
}