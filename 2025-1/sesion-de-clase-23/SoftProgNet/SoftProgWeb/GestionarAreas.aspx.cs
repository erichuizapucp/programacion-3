using System;
using PUCP.Edu.Pe.SoftProg.Web.SoftProgWS;

namespace PUCP.Edu.Pe.SoftProg.Web {
    public partial class GestionarAreas : System.Web.UI.Page {
        private area area;
        private readonly AreaWSClient areaWS;
        private estado estado;

        public GestionarAreas() {
            this.areaWS = new AreaWSClient();
        }

        protected void Page_Init(object sender, EventArgs e) {
            string accion = Request.QueryString["accion"];
            if (accion == null) {
                lblTitulo.Text = "Registrar Area";
                area = new area();
                estado = estado.Nuevo;
                if (!IsPostBack) { // Es la primera vez que se carga el formulario
                    Session["area"] = null;
                }
            }
            else if (accion == "modificar" && Session["area"] != null) {
                lblTitulo.Text = "Modificar Area";
                estado = estado.Modificar;
                area = (area)Session["area"];
                txtIDArea.Text = area.id.ToString();
                txtNombre.Text = area.nombre;
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e) {
            //Asignamos los valores
            area area;
            if (estado == estado.Nuevo) {
                area = new area {
                    nombre = txtNombre.Text
                };
            }
            else {
                area = new area {
                    id = int.Parse(txtIDArea.Text),
                    nombre = txtNombre.Text
                };
            }

            this.areaWS.guardarArea(area, estado);

            //Redireccionamos a otra página
            Response.Redirect("ListarAreas.aspx");
        }
    }
}