using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Linq;
using System.Linq.Expressions;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using PUCP.Edu.Pe.SoftProg.Web.SoftProgWS;

namespace PUCP.Edu.Pe.SoftProg.Web {
    public partial class GestionarOrdenesVenta : System.Web.UI.Page {
        private ClienteWSClient clienteWS;
        private ProductoWSClient productoWS;
        private OrdenVentaWSClient ordenVentaWS;
        private BindingList<cliente> clientes;
        private BindingList<producto> productos;

        protected void Page_Init(object sender, EventArgs e){
            clienteWS = new ClienteWSClient();
            clientes = new BindingList<cliente>(clienteWS.listarClientes());
            gvClientes.DataSource = clientes;
            gvClientes.DataBind();

            productoWS = new ProductoWSClient();
            productos = new BindingList<producto>(productoWS.listarProductos());
            gvProductos.DataSource = productos;
            gvProductos.DataBind();

            ordenVentaWS = new OrdenVentaWSClient();
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
                e.Row.Cells[0].Text = DataBinder.Eval(e.Row.DataItem, "dni").ToString();
                e.Row.Cells[1].Text = DataBinder.Eval(e.Row.DataItem, "nombre").ToString() + " " + DataBinder.Eval(e.Row.DataItem, "apellidoPaterno").ToString();
            }
        }

        protected void gvProductos_RowDataBound(object sender, GridViewRowEventArgs e) {
            if (e.Row.RowType == DataControlRowType.DataRow) {
            }
        }

        protected void lbSeleccionarCliente_Click(object sender, EventArgs e) {
            int idCliente = Int32.Parse(((LinkButton)sender).CommandArgument);
            cliente cliente = clientes.SingleOrDefault(x => x.id == idCliente);
            
            txtDNICliente.Text = cliente.dni;
            txtNombreCliente.Text = cliente.nombre + " " + cliente.apellidoPaterno;
        }

        protected void lbSeleccionarProducto_Click(object sender, EventArgs e) {
            int idProducto = Int32.Parse(((LinkButton)sender).CommandArgument);
            producto producto = productos.SingleOrDefault(x => x.id == idProducto);

            txtIDProducto.Text = producto.id.ToString();
            txtNombreProducto.Text = producto.nombre + " " + producto.unidadMedida;
            txtPrecioUnitProducto.Text = producto.precio.ToString("N2");
        }

        protected void lbAgregarLOV_Click(object sender, EventArgs e) {
            ordenVenta ordenVenta = new ordenVenta();
            ordenVenta.fechaHora = DateTime.Now;

            cliente cliente = this.clienteWS.buscarClientePorDni(txtDNICliente.Text);
            ordenVenta.cliente = cliente;
            ordenVenta.activo = true;

            lineaOrdenVenta linea = new lineaOrdenVenta();
            linea.producto = productoWS.obtenerProducto(Int32.Parse(txtIDProducto.Text));
            linea.cantidad = Int32.Parse(txtCantidadUnidades.Text);
            linea.subTotal = linea.producto.precio * linea.cantidad;
            linea.activo = true;

            ordenVenta.total = linea.subTotal + (linea.subTotal * 0.18);
            ordenVenta.lineasOrdenVenta = 
                (new List<lineaOrdenVenta> { linea }).ToArray();

            ordenVentaWS.guardarOrdenVenta(ordenVenta, estado.Nuevo);
        }
    }
}