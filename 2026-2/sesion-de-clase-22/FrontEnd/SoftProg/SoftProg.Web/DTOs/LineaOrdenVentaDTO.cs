namespace SoftProg.Web.DTOs {
    public class LineaOrdenVentaDTO {
        public int Id { get; set; }
        public ProductoDTO Producto { get; set; }
        public int Cantidad { get; set; }
        public double SubTotal { get; set; }
    }
}
