using SoftProg.Modelo.Almacen;

namespace SoftProg.Modelo.Ventas {
    public class LineaOrdenVenta : Registro {
        public LineaOrdenVenta() { }

        public LineaOrdenVenta(LineaOrdenVenta lineaOrdenVenta) : base(lineaOrdenVenta) {
            Producto = lineaOrdenVenta.Producto;
            Cantidad = lineaOrdenVenta.Cantidad;
            SubTotal = lineaOrdenVenta.SubTotal;
        }

        public Producto Producto {
            get {
                return new(field);
            }
            set {
                field = new(value);
            }
        }
        public int Cantidad { get; set; }
        public double SubTotal { get; set; }

        public override string ToString() {
            return base.ToString() + $"Producto: {Producto.Nombre}, Cantidad: {Cantidad}, SubTotal: {SubTotal:F2}";
        }
    }
}
