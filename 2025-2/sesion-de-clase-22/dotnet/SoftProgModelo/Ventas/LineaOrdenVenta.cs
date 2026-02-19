using PUCP.SoftProg.Modelo.Almacen;

namespace PUCP.SoftProg.Modelo.Ventas {
    public class LineaOrdenVenta : Registro {
        public OrdenVenta OrdenVenta { get; set; }
        public Producto Producto { get; set; }
        public int Cantidad { get; set; }
        public double SubTotal {  get; set; }
    }
}