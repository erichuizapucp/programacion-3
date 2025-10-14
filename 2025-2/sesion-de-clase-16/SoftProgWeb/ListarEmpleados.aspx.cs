using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.SoftProg.Modelo.RRHH;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;


namespace PUCP.SoftProg.Web {
    public partial class ListarEmpleados : System.Web.UI.Page {
        private IEmpleadoBO empleadoBO;
        private BindingList<Empleado> empleados;

        protected void Page_Load(object sender, EventArgs e) {
            empleadoBO = new EmpleadoBOImpl();

            if (!IsPostBack) {
                empleados = new BindingList<Empleado>(empleadoBO.Listar());
            }
            else {
                if (string.IsNullOrEmpty(txtNombreDNI.Text)) {
                    empleados = new BindingList<Empleado>(
                        empleadoBO.Listar());
                }
                else {
                    Empleado empl = empleadoBO.BuscarPorDni(txtNombreDNI.Text);
                    empleados = new BindingList<Empleado>(
                        new List<Empleado>() { empl });
                }
            }

            dgvEmpleados.DataSource = empleados;
            dgvEmpleados.DataBind();
        }

        protected void dgvEmpleados_RowDataBound(object sender, GridViewRowEventArgs e) {
            if(e.Row.RowType == DataControlRowType.DataRow) {
                e.Row.Cells[0].Text = DataBinder.Eval(e.Row.DataItem, "dni").ToString();
                e.Row.Cells[1].Text = 
                    DataBinder.Eval(e.Row.DataItem, "nombre").ToString() + " " + 
                    DataBinder.Eval(e.Row.DataItem, "apellidoPaterno").ToString();
                e.Row.Cells[2].Text = ((Area) DataBinder.Eval(e.Row.DataItem, "area")).Nombre;
            }
        }

        protected void dgvEmpleados_PageIndexChanging(object sender, GridViewPageEventArgs e) {
            dgvEmpleados.PageIndex = e.NewPageIndex;
            dgvEmpleados.DataBind();
        }

        protected void lbBuscar_Click(object sender, EventArgs e) {
            if (string.IsNullOrEmpty(txtNombreDNI.Text)) {
                empleados = new BindingList<Empleado>(empleadoBO.Listar());
            }
            else {
                Empleado empl = empleadoBO.BuscarPorDni(txtNombreDNI.Text);
                empleados = new BindingList<Empleado>(new List<Empleado>() { empl });
            }

            dgvEmpleados.DataSource = empleados;
            dgvEmpleados.DataBind();
        }

        protected void lbModificar_Click(object sender, EventArgs e) {
            int idEmpleado = int.Parse(((LinkButton)sender).CommandArgument);
            Empleado empleado = empleados.SingleOrDefault(x => x.Id == idEmpleado);
            Session["empleado"] = empleado;
            Response.Redirect("GestionarEmpleados.aspx?accion=modificar");
        }

        protected void lbEliminar_Click(object sender, EventArgs e) {
            int idEmpleado = int.Parse(((LinkButton)sender).CommandArgument);
            empleadoBO.Eliminar(idEmpleado);
            Response.Redirect("ListarEmpleados.aspx");
        }

        protected void lbRegistrar_Click(object sender, EventArgs e) {
            Response.Redirect("GestionarEmpleados.aspx");
        }
    }
}