namespace SoftProg.Web.ViewModels {
    public class LineaOrdenVentaViewModel {
        public int Id { get; set; }

        public int IdProducto { get; set; }

        public string NombreProducto { get; set; } = "";

        public double Precio { get; set; }

        public int Cantidad { get; set; }

        public double SubTotal { get; set; }
    }
}
