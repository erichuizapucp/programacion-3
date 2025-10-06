using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using PUCP.SoftProg.Persistencia.DAOImpl.RRHH;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace PUCP.SoftProg.Web {
    public partial class GestionarEmpleados : System.Web.UI.Page {
        private IAreaBO areaBO;
        private IEmpleadoBO empleadoBO;
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
                estado = Estado.Modificado;
                empleado = (Empleado)Session["empleado"];
                hdnIdEmpleado.Value = empleado.Id.ToString();
                txtDNIEmpleado.Text = empleado.Dni;
                ddlCargo.SelectedValue = empleado.Cargo.ToString();
                txtApellidoPaterno.Text = empleado.ApellidoPaterno;
                txtNombre.Text = empleado.Nombre;

                if (empleado.Genero == Genero.MASCULINO) {
                    rbMasculino.Checked = true;
                }
                else {
                    rbFemenino.Checked = true;
                }

                txtSueldo.Text = empleado.Sueldo.ToString("N2");
                ddlAreas.SelectedValue = empleado.Area.Id.ToString();
                dtpFechaNacimiento.Value = empleado.FechaNacimiento.ToString("yyyy-MM-dd");
            }
        }

        protected void GuardarEmpleado(object sender, EventArgs e) {
            empleadoBO = new EmpleadoBOImpl();

            //Asignamos los valores
            if (estado == Estado.Modificado) {
                empleado.Id = int.Parse(hdnIdEmpleado.Value);
            }

            empleado.Dni = txtDNIEmpleado.Text;
            empleado.Nombre = txtNombre.Text;
            empleado.ApellidoPaterno = txtApellidoPaterno.Text;
            empleado.Area = new Area {
                Id = int.Parse(ddlAreas.SelectedValue)
            };

            if (rbMasculino.Checked) {
                empleado.Genero = Genero.MASCULINO;
            }
            else {
                empleado.Genero = Genero.FEMENINO;
            }

            empleado.FechaNacimiento = DateTime.Parse(dtpFechaNacimiento.Value);
            empleado.Cargo = (Cargo)Enum.Parse(typeof(Cargo), ddlCargo.SelectedItem.Value);
            empleado.Sueldo = double.Parse(txtSueldo.Text);

            empleadoBO.Guardar(empleado, estado);
            Response.Redirect("ListarEmpleados.aspx");
        }
    }
}