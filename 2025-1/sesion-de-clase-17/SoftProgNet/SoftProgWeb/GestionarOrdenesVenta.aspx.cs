using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Linq.Expressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.Edu.Pe.SoftProg.Modelo.Clientes;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Ventas;
using PUCP.Edu.Pe.SoftProg.Negocio.BO;
using PUCP.Edu.Pe.SoftProg.Negocio.BOImpl;

namespace PUCP.Edu.Pe.SoftProg.Web {
    public partial class GestionarOrdenesVenta : System.Web.UI.Page {
        private IClienteBO clienteBO;
        private IProductoBO productoBO;
        private IOrdenVentaBO ordenVentaBO;
        private BindingList<Cliente> clientes;
        private BindingList<Producto> productos;

        protected void Page_Init(object sender, EventArgs e){
            clienteBO = new ClienteBOImpl();
            clientes = new BindingList<Cliente>(clienteBO.Listar());
            gvClientes.DataSource = clientes;
            gvClientes.DataBind();

            productoBO = new ProductoBOImpl();
            productos = new BindingList<Producto>(productoBO.Listar());
            gvProductos.DataSource = productos;
            gvProductos.DataBind();

            ordenVentaBO = new OrdenVentaBOImpl();
        }

        protected void btnBuscarCliente_Click(object sender, EventArgs e) {
            //Llamar a una función Javascript
            string script = "window.onload = function() { showModalFormCliente()};";
            ClientScript.RegisterStartupScript(GetType(),"",script, true);
        }

        protected void btnBuscarProducto_Click(object sender, EventArgs e) {
            //Llamar a una función Javascript
            string script = "window.onload = function() { showModalFormProducto()};";
            ClientScript.RegisterStartupScript(GetType(), "", script, true);
        }

        protected void gvClientes_RowDataBound(object sender, GridViewRowEventArgs e) {
            if (e.Row.RowType == DataControlRowType.DataRow) {
                e.Row.Cells[0].Text = DataBinder.Eval(e.Row.DataItem, "Dni").ToString();
                e.Row.Cells[1].Text = DataBinder.Eval(e.Row.DataItem, "Nombre").ToString() + " " + DataBinder.Eval(e.Row.DataItem, "ApellidoPaterno").ToString();
            }
        }

        protected void gvProductos_RowDataBound(object sender, GridViewRowEventArgs e) {
            if (e.Row.RowType == DataControlRowType.DataRow) {
            }
        }

        protected void lbSeleccionarCliente_Click(object sender, EventArgs e) {
            int idCliente = Int32.Parse(((LinkButton)sender).CommandArgument);
            Cliente cliente = clientes.SingleOrDefault(x => x.Id == idCliente);
            
            txtDNICliente.Text = cliente.Dni;
            txtNombreCliente.Text = cliente.Nombre + " " + cliente.ApellidoPaterno;
        }

        protected void lbSeleccionarProducto_Click(object sender, EventArgs e) {
            int idProducto = Int32.Parse(((LinkButton)sender).CommandArgument);
            Producto producto = productos.SingleOrDefault(x => x.Id == idProducto);

            txtIDProducto.Text = producto.Id.ToString();
            txtNombreProducto.Text = producto.Nombre + " " + producto.UnidadMedida;
            txtPrecioUnitProducto.Text = producto.Precio.ToString("N2");
        }

        protected void lbAgregarLOV_Click(object sender, EventArgs e) {
            OrdenVenta ordenVenta = new OrdenVenta();
            ordenVenta.FechaHora = DateTime.Now;

            Cliente cliente = this.clienteBO.BuscarPorDni(txtDNICliente.Text);
            ordenVenta.Cliente = cliente;
            ordenVenta.IsActive = true;

            LineaOrdenVenta linea = new LineaOrdenVenta();
            linea.Producto = productoBO.Obtener(Int32.Parse(txtIDProducto.Text));
            linea.Cantidad = Int32.Parse(txtCantidadUnidades.Text);
            linea.SubTotal = linea.Producto.Precio * linea.Cantidad;
            linea.IsActive = true;

            ordenVenta.Total = linea.SubTotal + (linea.SubTotal * 0.18);
            ordenVenta.LineasOrdenVenta = new List<LineaOrdenVenta> { linea };

            ordenVentaBO.Guardar(ordenVenta, Negocio.Estado.Nuevo);
        }
    }
}