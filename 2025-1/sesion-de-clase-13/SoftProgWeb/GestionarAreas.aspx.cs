using System;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Negocio;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Negocio.BOImpl;

namespace PUCP.Edu.Pe.SoftProg.Web {
    public partial class GestionarAreas : System.Web.UI.Page {
        private Area area;
        private readonly IAreaBO areaBO;
        private Estado estado;

        public GestionarAreas() {
            this.areaBO = new AreaBOImpl();
        }

        protected void Page_Init(object sender, EventArgs e) {
            string accion = Request.QueryString["accion"];
            if (accion == null) {
                lblTitulo.Text = "Registrar Area";
                area = new Area();
                estado = Estado.Nuevo;
                if (!IsPostBack) { // Es la primera vez que se carga el formulario
                    Session["area"] = null;
                }
            }
            else if (accion == "modificar" && Session["area"] != null) {
                lblTitulo.Text = "Modificar Area";
                estado = Estado.Modificar;
                area = (Area)Session["area"];
                txtIDArea.Text = area.Id.ToString();
                txtNombre.Text = area.Nombre;
            }
        }

        protected void btnGuardar_Click(object sender, EventArgs e) {
            //Asignamos los valores
            Area area;
            if (estado == Estado.Nuevo) {
                area = new Area {
                    Nombre = txtNombre.Text
                };
            }
            else {
                area = new Area {
                    Id = int.Parse(txtIDArea.Text),
                    Nombre = txtNombre.Text
                };
            }

            this.areaBO.Guardar(area, estado);

            //Redireccionamos a otra página
            Response.Redirect("ListarAreas.aspx");
        }
    }
}