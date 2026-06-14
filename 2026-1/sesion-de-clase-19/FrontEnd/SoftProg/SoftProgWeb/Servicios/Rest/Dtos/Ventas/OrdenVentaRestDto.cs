namespace SoftProgWeb.Servicios.Rest.Dtos.Ventas;

public sealed class OrdenVentaRestDto {
    public int Id { get; set; }
    public bool Activo { get; set; }
    public string? Fecha { get; set; }
    public double Total { get; set; }
    public ClienteRestDto? Cliente { get; set; }
    public List<LineaOrdenVentaRestDto> Lineas { get; set; } = [];
}
