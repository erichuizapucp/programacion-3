using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Almacen;

namespace PUCP.Edu.Pe.SoftProg.Modelo.Logistica.Ventas {
    public class LineaOrdenVenta : ModeloBase {
        public OrdenVenta OrdenVenta { get; set; }
        public Producto Producto { get; set; }
        public int Cantidad { get; set; }
        public double SubTotal {  get; set; }
    }
}