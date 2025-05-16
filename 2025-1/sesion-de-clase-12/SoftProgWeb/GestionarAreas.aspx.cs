using System;
using PUCP.Edu.Pe.SoftProg.Modelo.RRHH;
using PUCP.Edu.Pe.SoftProg.Negocio;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAO.RRHH;
using PUCP.Edu.Pe.SoftProg.Persistencia.DAOImpl.RRHH;

namespace PUCP.Edu.Pe.SoftProg.Web {
    public partial class GestionarAreas : System.Web.UI.Page {
        private Area area;
        private readonly IAreaDAO areaDAO;
        private Estado estado;

        public GestionarAreas() {
            this.areaDAO = new AreaDAOImpl();
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
                this.areaDAO.Insertar(area);
            }
            else {
                area = new Area {
                    Id = int.Parse(txtIDArea.Text),
                    Nombre = txtNombre.Text
                };
                this.areaDAO.Modificar(area);
            }

            //Redireccionamos a otra página
            Response.Redirect("ListarAreas.aspx");
        }
    }
}