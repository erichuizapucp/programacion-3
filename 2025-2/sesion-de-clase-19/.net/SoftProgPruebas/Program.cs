using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.SoftProg.Modelo.Almacen;
using PUCP.SoftProg.Negocio.BO;
using PUCP.SoftProg.Negocio.BOImpl;

namespace SoftProgPruebas {
    public class Program {
        public static void Main(string[] args) {
            IProductoBO productoBO = new ProductoBOImpl();
            List<Producto> productos = productoBO.Listar();


        }
    }
}
