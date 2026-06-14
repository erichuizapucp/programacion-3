namespace SoftProgWeb.Servicios.Rest.Dtos.Ventas;

public sealed class LineaOrdenVentaRestDto {
    public int Id { get; set; }
    public bool Activo { get; set; }
    public int Cantidad { get; set; }
    public double SubTotal { get; set; }
    public ProductoRestDto? Producto { get; set; }
}
