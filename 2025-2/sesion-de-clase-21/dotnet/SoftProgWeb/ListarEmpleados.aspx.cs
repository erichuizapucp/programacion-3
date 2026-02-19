using PUCP.SoftProg.Web.SoftProgWS;
using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;


namespace PUCP.SoftProg.Web {
    public partial class ListarEmpleados : System.Web.UI.Page {
        private EmpleadoWSClient clientWS;
        private BindingList<empleado> empleados;

        protected void Page_Load(object sender, EventArgs e) {
            clientWS = new EmpleadoWSClient();

            if (!IsPostBack) {
                empleados = new BindingList<empleado>(clientWS.listarEmpleados());
            }
            else {
                if (string.IsNullOrEmpty(txtNombreDNI.Text)) {
                    empleados = new BindingList<empleado>(
                        clientWS.listarEmpleados());
                }
                else {
                    empleado empl = clientWS.buscarEmpleadoPorDni(txtNombreDNI.Text);
                    empleados = new BindingList<empleado>(
                        new List<empleado>() { empl });
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
                e.Row.Cells[2].Text = ((area) DataBinder.Eval(e.Row.DataItem, "area")).nombre;
            }
        }

        protected void dgvEmpleados_PageIndexChanging(object sender, GridViewPageEventArgs e) {
            dgvEmpleados.PageIndex = e.NewPageIndex;
            dgvEmpleados.DataBind();
        }

        protected void lbBuscar_Click(object sender, EventArgs e) {
            if (string.IsNullOrEmpty(txtNombreDNI.Text)) {
                empleados = new BindingList<empleado>(clientWS.listarEmpleados());
            }
            else {
                empleado empl = clientWS.buscarEmpleadoPorDni(txtNombreDNI.Text);
                empleados = new BindingList<empleado>(new List<empleado>() { empl });
            }

            dgvEmpleados.DataSource = empleados;
            dgvEmpleados.DataBind();
        }

        protected void lbModificar_Click(object sender, EventArgs e) {
            int idEmpleado = int.Parse(((LinkButton)sender).CommandArgument);
            empleado empleado = empleados.SingleOrDefault(x => x.id == idEmpleado);
            Session["empleado"] = empleado;
            Response.Redirect("GestionarEmpleados.aspx?accion=modificar");
        }

        protected void lbEliminar_Click(object sender, EventArgs e) {
            int idEmpleado = int.Parse(((LinkButton)sender).CommandArgument);
            clientWS.eliminarEmpleado(idEmpleado);
            Response.Redirect("ListarEmpleados.aspx");
        }

        protected void lbRegistrar_Click(object sender, EventArgs e) {
            Response.Redirect("GestionarEmpleados.aspx");
        }
    }
}