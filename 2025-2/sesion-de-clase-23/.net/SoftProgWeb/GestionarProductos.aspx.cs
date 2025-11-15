using PUCP.SoftProg.Web.SoftProgWS;
using System;

namespace PUCP.SoftProg.Web {
    public partial class GestionarProductos : System.Web.UI.Page {
        private ProductoWSClient productoWS;
        private producto producto;
        private estado estado;

        protected void Page_Load(object sender, EventArgs e) {
            string accion = Request.QueryString["accion"];
            estado = accion == null ? estado.Nuevo : estado.Modificado;

            if (!IsPostBack) {
                if (estado == estado.Nuevo) {
                    lblTitulo.Text = "Registrar Producto";
                    producto = new producto();
                    estado = estado.Nuevo;
                    Session["producto"] = null;
                }
                else if (estado == estado.Modificado && Session["producto"] != null) {
                    lblTitulo.Text = "Modificar Producto";
                    estado = estado.Modificado;
                    producto = (producto)Session["producto"];
                    txtIDProducto.Text = producto.id.ToString();
                    txtNombre.Text = producto.nombre;
                    ddlUnidadMedida.Text = producto.unidadMedida.ToString();
                    txtPrecio.Text = producto.precio.ToString("N2");
                }
            }
            else {
                producto = (producto)Session["producto"];
            }
        }

        protected void GuardarProducto(object sender, EventArgs e) {
            productoWS = new ProductoWSClient();

            //Asignamos los valores
            if (estado == estado.Modificado) {
                producto.id = int.Parse(txtIDProducto.Text);
            }
            
            producto.nombre = txtNombre.Text;
            producto.unidadMedida = (unidadMedida)Enum.Parse(typeof(unidadMedida), ddlUnidadMedida.SelectedItem.Value);
            producto.precio = double.Parse(txtPrecio.Text);

            productoWS.guardarProducto(producto, estado);
            Response.Redirect("ListarProductos.aspx");
        }
    }
}