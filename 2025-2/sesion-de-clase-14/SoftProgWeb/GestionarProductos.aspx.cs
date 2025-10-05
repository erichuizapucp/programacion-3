using PUCP.SoftProg.Modelo;
using PUCP.SoftProg.Modelo.Almacen;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;
using System;

namespace PUCP.SoftProg.Web {
    public partial class GestionarProductos : System.Web.UI.Page {
        private IProductoBO productoBO;
        private Producto producto;
        private Estado estado;

        protected void Page_Load(object sender, EventArgs e) {
            string accion = Request.QueryString["accion"];
            estado = accion == null ? Estado.Nuevo : Estado.Modificado;

            if (!IsPostBack) {
                if (estado == Estado.Nuevo) {
                    lblTitulo.Text = "Registrar Producto";
                    producto = new Producto();
                    estado = Estado.Nuevo;
                    Session["producto"] = null;
                }
                else if (estado == Estado.Modificado && Session["producto"] != null) {
                    lblTitulo.Text = "Modificar Producto";
                    estado = Estado.Modificado;
                    producto = (Producto)Session["producto"];
                    txtIDProducto.Text = producto.Id.ToString();
                    txtNombre.Text = producto.Nombre;
                    ddlUnidadMedida.Text = producto.UnidadMedida.ToString();
                    txtPrecio.Text = producto.Precio.ToString("N2");
                }
            }
            else {
                producto = (Producto)Session["producto"];
            }
        }

        protected void GuardarProducto(object sender, EventArgs e) {
            productoBO = new ProductoBOImpl();

            //Asignamos los valores
            if (estado == Estado.Modificado) {
                producto.Id = int.Parse(txtIDProducto.Text);
            }
            
            producto.Nombre = txtNombre.Text;
            producto.UnidadMedida = (UnidadMedida)Enum.Parse(typeof(UnidadMedida), ddlUnidadMedida.SelectedItem.Value);
            producto.Precio = double.Parse(txtPrecio.Text);

            productoBO.Guardar(producto, estado);
            Response.Redirect("ListarProductos.aspx");
        }
    }
}